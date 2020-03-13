package ru.ifmo.ivshin.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class Implementor implements JarImpler {
    /**
     * System-dependent line separator string.
     */
    private static final String EOL = System.lineSeparator();

    /**
     * Produces code implementing class or interface specified by provided {@code token}.
     * <p>
     * Generated class classes name should be same as classes name of the type token with {@code Impl} suffix
     * added. Generated source code should be placed in the correct subdirectory of the specified
     * {@code root} directory and have correct file name. For example, the implementation of the
     * interface {@link List} should go to {@code $root/java/util/ListImpl.java}
     *
     * @param token type token to create implementation for.
     * @param root  root directory.
     * @throws ImplerException when implementation cannot be generated for one of such reasons:
     *                         <ul>
     *                         <li> one of arguments is null</li>
     *                         <li> {@code token} is primitive or array</li>
     *                         <li> {@code token} is final or private</li>
     *                         <li> {@code token} is {@link Enum}</li>
     *                         <li> Error occurred during writing or creating files</li>
     *                         </ul>
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null || root == null) {
            throw new ImplerException("Arguments must be not-null");
        }

        if (token.isPrimitive() || token.isArray() || Modifier.isFinal(token.getModifiers()) || Modifier.isPrivate(token.getModifiers()) || token == Enum.class) {
            throw new ImplerException("Invalid class token");
        }

        Path filePath = resolveFilePath(token, root, "java");

        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException | SecurityException e) {
            throw new ImplerException("Failed to create directories", e);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            implement(token, writer);
        } catch (IOException e) {
            throw new ImplerException("Can't write to " + filePath.toString(), e);
        }
    }

    /**
     * Returns path to file containing implementation of {@code token} with specified {@code extension}
     *
     * @param token     type token to return path for
     * @param root      root directory
     * @param extension file extension
     * @return path to file
     */
    private Path resolveFilePath(Class<?> token, Path root, String extension) {
        return root.resolve(token.getPackageName().replace(".", File.separator))
                .resolve(token.getSimpleName() + "Impl." + extension);
    }

    /**
     * Produces {@code .jar} file implementing class or interface specified by provided {@code token}.
     * <p>
     * Generated class classes name should be same as classes name of the type token with {@code Impl} suffix
     * added.
     *
     * @param token   type token to create implementation for.
     * @param jarFile target {@code .jar} file.
     * @throws ImplerException when implementation cannot be generated for one of such reasons:
     *                         <ul>
     *                         <li> one of arguments is null</li>
     *                         <li> {@code token} is primitive or array</li>
     *                         <li> {@code token} is final or private</li>
     *                         <li> {@code token} is {@link Enum}</li>
     *                         <li> Error occurred during writing, creating files, or compiling</li>
     *                         </ul>
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        if (token == null || jarFile == null) {
            throw new ImplerException("Arguments can not be null");
        }

        Path tempDir;
        try {
            tempDir = Files.createTempDirectory(jarFile.toAbsolutePath().getParent(), "tmp");
        } catch (IOException e) {
            throw new ImplerException("Failed to create temporary directory", e);
        }

        try {
            implement(token, tempDir);
            compileImplementedFile(token, tempDir);
            createJarFile(token, tempDir, jarFile);
        } finally {
            deleteDirectoryOnExit(tempDir);
        }
    }

    /**
     * Produces {@code .jar} file implementing class or interface specified by provided {@code token}.
     *
     * @param token   type token to create implementation for
     * @param tempDir directory where {@code token} implementation was compiled via
     *                {@link #compileImplementedFile(Class, Path) compileImplementedFile(token, tempDir}
     * @param jarFile target {@code .jar} file
     * @throws ImplerException if an I/O error thrown during writing to jar file
     */
    private void createJarFile(Class<?> token, Path tempDir, Path jarFile) throws ImplerException {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream writer = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            writer.putNextEntry(new ZipEntry((token.getPackageName() + "." + token.getSimpleName())
                    .replace('.', '/') + "Impl.class"));
            Files.copy(resolveFilePath(token, tempDir, "class"), writer);
        } catch (IOException e) {
            throw new ImplerException("Failed to write to JAR file", e);
        }
    }

    /**
     * Compiles java file which was implemented via {@link #implement(Class, Path) implement(token, root)}.
     *
     * @param token class which was implemented
     * @param root  path where class was implemented
     * @throws ImplerException if an error occurred during compiling
     */
    private void compileImplementedFile(Class<?> token, Path root) throws ImplerException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String[] args = new String[]{
                "-cp",
                root.toString() + File.pathSeparator + System.getProperty("java.class.path"),
                resolveFilePath(token, root, "java").toString()};
        if (compiler == null || compiler.run(null, null, null, args) != 0) {
            throw new ImplerException("Failed to compile implemented file");
        }
    }

    /**
     * Writes code implementation of the {@code token} class to specified {@code writer}.
     *
     * @param token  class the header of which is to be written
     * @param writer to write to
     * @throws IOException     if an I/O error thrown during writing
     * @throws ImplerException if provided {@code token} is not an interface and it doesn't have any public or protected constructors
     */
    private void implement(Class<?> token, Writer writer) throws IOException, ImplerException {
        writePackage(token, writer);
        writeClassHeader(token, writer);

        writer.write("{" + EOL);

        if (!token.isInterface()) {
            throw new ImplerException("Incorrect token");
        }
        writeMethods(token, writer);

        writer.write("}" + EOL);
    }

    /**
     * Writes the package of the {@code token} class to the specified {@code writer}.
     *
     * @param token  class the package of which is to be written
     * @param writer to write to
     * @throws IOException if an I/O error thrown during writing
     */
    private void writePackage(Class<?> token, Writer writer) throws IOException {
        String packageName = token.getPackageName();
        if (!packageName.equals("")) {
            writer.write(toUnicode(String.format("package %s;%n%n", packageName)));
        }
    }

    /**
     * Writes the header of the {@code token} class declaration to the specified {@code writer}.
     *
     * @param token  class the header of which is to be written
     * @param writer to write to
     * @throws IOException if an I/O error thrown during writing
     */
    private void writeClassHeader(Class<?> token, Writer writer) throws IOException {
        writer.write(toUnicode(String.format("public class %sImpl %s %s ",
                token.getSimpleName(), "implements",
                token.getCanonicalName())));
    }

    /**
     * Writes built abstract methods of {@code token} class to specified {@code writer}.
     *
     * @param token  class whose methods are to be written
     * @param writer to write to
     * @throws IOException if an I/O error thrown during writing
     */
    private void writeMethods(Class<?> token, Writer writer) throws IOException {
        for (Method method : token.getMethods()) {
            writer.write(toUnicode(buildExecutable(method)));
        }
    }


    /**
     * Returns java code as a {@link String} that representing compilable
     * {@link Method} or {@link Constructor}.
     *
     * @param executable object which representation is to be returned
     * @return {@link String} representation of {@code executable}
     */
    private String buildExecutable(Executable executable) {
        return "\tpublic " + buildReturnTypeAndName(executable) +
                "(" + buildParameters(executable) + ")" +
                buildExceptions(executable) + " {" + EOL +
                "\t\t" + "return" + buildDefaultValue(((Method) executable).getReturnType()) + ";" + EOL +
                "\t}" + EOL;
    }

    /**
     * Returns return type in canonical form (if {@code executable} is instance of {@link Method})
     * and name of {@code executable} object.
     *
     * @param executable object which return type and name is to be returned
     * @return {@link String} representation of return type (if needed) and name of
     * {@code executable} object
     */

    private String buildReturnTypeAndName(Executable executable) {
        if (executable instanceof Method) {
            return ((Method) executable).getReturnType().getCanonicalName() + " " + executable.getName();
        } else {
            return executable.getDeclaringClass().getSimpleName() + "Impl";
        }
    }

    /**
     * Returns list of exceptions, that specified {@code executable} throws. If {@code executable}
     * doesn't throw any exceptions returns an empty string. Otherwise the format is
     * the word {@code throws}, followed by a comma-separated list of the thrown exception types
     * in canonical form.
     *
     * @param executable object whose exceptions is to be returned
     * @return string representation of exceptions thrown by {@code executable} if any,
     * otherwise an empty string
     */
    private String buildExceptions(Executable executable) {
        Class<?>[] exceptions = executable.getExceptionTypes();
        if (exceptions.length > 0) {
            return " throws " + Arrays.stream(exceptions)
                    .map(Class::getCanonicalName)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }

    /**
     * Returns default value of specified {@code token}. In the
     * case when token is void.class method returns an empty string
     *
     * @param token whose default value is to be returned
     * @return string representation of the default value
     */
    private String buildDefaultValue(Class<?> token) {
        if (token == boolean.class) {
            return " false";
        } else if (token == void.class) {
            return "";
        } else if (token.isPrimitive()) {
            return " 0";
        }
        return " null";
    }

    /**
     * Returns a string describing parameters of {@code executable}. The format is
     * comma-separated list of the {@code executable}'s parameters names with types
     * (if {@code withType} is true) in canonical form.
     *
     * @param executable object whose parameters are to be returned
     * @return list of parameters with names and types if needed
     */
    private String buildParameters(Executable executable) {
        return Arrays.stream(executable.getParameters())
                .map(p -> buildParameter(p, true))
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns a string describing this {@code parameter}. The format is the
     * type (if {@code withType} is true} in canonical name, followed by a space,
     * followed by the name of the parameter
     *
     * @param parameter whose representation is to be returned
     * @param withType  determines whether the type of parameters is necessary
     * @return a string representation of the {@code parameter}
     */
    private String buildParameter(Parameter parameter, boolean withType) {
        return (withType ? parameter.getType().getCanonicalName() + " " : "") + parameter.getName();
    }

    /**
     * Requests that the {@code directory} and all including files
     * be deleted when the virtual machine terminates.
     * Deletion will be attempted only for normal termination of the
     * virtual machine, as defined by the Java Language Specification.
     *
     * @param directory to be deleted
     * @throws ImplerException if an error is thrown when accessing the starting file.
     * @see File#deleteOnExit()
     */
    private void deleteDirectoryOnExit(Path directory) throws ImplerException {
        try {
            Files.walk(directory).map(Path::toFile).forEach(File::deleteOnExit);
        } catch (IOException | SecurityException e) {
            throw new ImplerException("Failed to delete temporary files", e);
        }
    }

    /**
     * Converts non-ASCII characters in the given string to unicode escaping
     *
     * @param s to convert
     * @return converted string
     */
    private static String toUnicode(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c >= 128) {
                sb.append(String.format("\\u%04X", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Entry point into the application.
     *
     * @param args Usage:
     *             <ul>
     *             <li> {@code <classToken> <rootPath>} to produce file implementing {@code classToken} in {@code rootPath}</li>
     *             <li>{@code -jar <classToken> <jarPath>} to produce jar file in {@code jarPath} implementing {@code classToken}</li>
     *             </ul>
     */
    static public void main(String[] args) {
        if (args == null || args.length < 2 || args.length > 3) {
            System.err.println("Excepted two or three arguments");
            return;
        }

        if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            System.err.println("One of arguments is null");
        }

        Implementor implementor = new Implementor();

        try {
            if (args.length == 2) {
                implementor.implement(Class.forName(args[0]), Paths.get(args[1]));
            } else {
                implementor.implementJar(Class.forName(args[1]), Paths.get(args[2]));
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid class name: " + e.getMessage());
        } catch (ImplerException e) {
            System.err.println(e.getMessage());
        } catch (InvalidPathException e) {
            System.err.println("Invalid root path: " + e.getMessage());
        }

    }
}
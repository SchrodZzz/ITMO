package ru.ifmo.ivshin.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Implementation class for {@link JarImpler} interface
 * @version 1.0
 * @author Fat Lion
 */
public class Implementor implements JarImpler {

    /**
     * @throws ImplerException if the given class cannot be generated for one of such reasons:
     *                         <ul>
     *                             <li>token is null</li>
     *                             <li>root is null</li>
     *                             <li>token isn't interface</li>
     *                             <li>token is private</li>
     *                             <li>writing exception</li>
     *                         </ul>
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null || root == null) {
            throw new ImplerException("Not-null arguments expected");
        }
        if (!token.isInterface() || Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Incorrect class token");
        }
        try (BufferedWriter writer = Files.newBufferedWriter(createFilePath(token, root), StandardCharsets.UTF_8)) {
            StringBuilder builder = new StringBuilder();
            builder.append("public class ")
                    .append(token.getSimpleName())
                    .append("Impl")
                    .append(" implements ")
                    .append(token.getCanonicalName())
                    .append(" {");
            for (Method method : token.getMethods()) {
                builder.append("\n\t")
                        .append("public ")
                        .append(method.getReturnType().getCanonicalName())
                        .append(" ")
                        .append(method.getName()).append("(");
                builder.append(Arrays.stream(method.getParameters())
                        .map(t -> t.getType().getCanonicalName() + " " + t.getName())
                        .collect(Collectors.joining(", ")));
                builder.append(")");
                if (method.getExceptionTypes().length > 0) {
                    builder.append(" throws ");
                    builder.append(Arrays.stream(method.getExceptionTypes())
                            .map(Class::getCanonicalName)
                            .collect(Collectors.joining(", ")));
                }
                builder.append(" {");
                builder.append("\n\t\t")
                        .append("return ")
                        .append(getDefaultValue(method.getReturnType()))
                        .append(";\n");
                builder.append("\t").append("}\n");
            }
            builder.append("}");
            writer.write(token.getPackage() == null ? "" : "package " + token.getPackageName() + ";\n\n");
            writer.write(builder.toString());
        } catch (IOException e) {
            throw new ImplerException("Writing exception", e);
        }
    }

    /**
     * Produces {@code jar} file implementing interface specified by provided {@code token}.
     * <p>
     * Generated class full name should be same as full name of the type token with {@code Impl} suffix
     * added.
     * <p>
     * During implementation creates temporary folder to store temporary {@code .java} and {@code .class} files.
     *
     * @throws ImplerException if the given class cannot be generated for one of such reasons:
     *                         <ul>
     *                         <li> Some arguments are {@code null}</li>
     *                         <li> Error occurs during implementation via {@link #implement(Class, Path)} </li>
     *                         <li> {@link JavaCompiler} failed to compile implemented class </li>
     *                         <li> The problems with I/O occurred during implementation. </li>
     *                         </ul>
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        if (token == null || jarFile == null) {
            throw new ImplerException("Not-null arguments expected");
        }
        Path tmp;
        try {
            tmp = Files.createTempDirectory(jarFile.toAbsolutePath().getParent(), "tmp");
        } catch (IOException e) {
            throw new ImplerException("Unable to create tmp directory", e);
        }
        implement(token, tmp);
        compile(tmp, getClassPath(token, tmp));
        createJar(token, tmp, jarFile);
        clear(tmp.toFile());
    }

    /**
     * Create file path for generating class.
     *
     * @param token for implementing
     * @param root  start path to class
     * @return {@link Path path} to generating class
     * @throws IOException fail while creating directories
     */
    private Path createFilePath(Class<?> token, Path root) throws IOException {
        Path fileDir = getDirectory(token, root);
        Files.createDirectories(fileDir);
        return fileDir.resolve(token.getSimpleName() + "Impl.java");
    }

    /**
     * Gets directory for generating class.
     *
     * @param token for implementing
     * @param root  start path to class
     * @return {@link Path path} to directory of generating class
     */
    private Path getDirectory(Class<?> token, Path root) {
        return token.getPackage() == null
                ? root
                : root.resolve(token.getPackageName().replace(".", File.separator));
    }

    /**
     * Gets path to generating class.
     *
     * @param token for implementing
     * @param root  start path to class
     * @return {@link Path path} to generating class
     */
    private Path getClassPath(Class<?> token, Path root) {
        return getDirectory(token, root).resolve(token.getSimpleName() + "Impl");
    }

    /**
     * Gets default value of given class
     *
     * @param token class to get default value
     * @return {@link String} representing value
     */
    private String getDefaultValue(Class<?> token) {
        if (token.equals(boolean.class)) {
            return " false";
        } else if (token.equals(void.class)) {
            return "";
        } else if (token.isPrimitive()) {
            return " 0";
        }
        return " null";
    }

    /**
     * Compiles class.
     *
     * @param root      start path to class
     * @param classPath to generated class
     * @throws ImplerException <ul>
     *                         <li>Compiler not found</li>
     *                         <li>Compiling error</li>
     *                         </ul>
     */
    private void compile(Path root, Path classPath) throws ImplerException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Compiler not found");
        }
        if (compiler.run(null, null, null,
                classPath + ".java",
                "-cp",
                root + File.pathSeparator + System.getProperty("java.class.path")) != 0) {
            throw new ImplerException("Unable to compile generated files");
        }
    }

    /**
     * Creates jar file.
     *
     * @param token   for implementing
     * @param root    tart path to class
     * @param jarFile to creating jar file
     * @throws ImplerException The problems with I/O occurred
     */
    private void createJar(Class<?> token, Path root, Path jarFile) throws ImplerException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream writer = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            writer.putNextEntry(new ZipEntry(getClassPath(token, Paths.get("")) + ".class"));
            Files.copy(Paths.get(getClassPath(token, root) + ".class"), writer);
        } catch (IOException e) {
            throw new ImplerException("Cannot create jar file", e);
        }
    }

    /**
     * Deletes directory represented by {@code path}
     *
     * @param path directory to be deleted
     */
    private void clear(File path) {
        if (path.isDirectory()) {
            for (File underFile : Objects.requireNonNull(path.listFiles())) {
                clear(underFile);
            }
        }
        path.delete();
    }


    /**
     * This function is used to choose which way of implementation to execute.
     * Runs {@link Implementor} in two possible ways:
     * <ul>
     * <li> 2 arguments: {@code className rootPat} - runs {@link #implement(Class, Path)} with given arguments</li>
     * <li> 3 arguments: {@code -jar className jarPath} - runs {@link #implementJar(Class, Path)} with two second arguments</li>
     * </ul>
     * If arguments are incorrect or an error occurs during implementation returns message with information about error
     *
     * @param args arguments for running an application
     */
    public static void main(String[] args) {
        if (args == null || args.length < 2 || args.length > 3) {
            System.out.println("Two or three arguments expected");
            return;
        }
        Implementor implementor = new Implementor();
        try {
            if ("-jar".equals(args[0])) {
                implementor.implementJar(Class.forName(args[1]), Paths.get(args[2]));
            } else {
                implementor.implement(Class.forName(args[0]), Paths.get(args[1]));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong token " + e.getMessage());
        } catch (InvalidPathException e) {
            System.out.println("Wrong file path " + e.getMessage());
        } catch (ImplerException e) {
            System.out.println("An error occurred during implementation: " + e.getMessage());
        }
    }
}
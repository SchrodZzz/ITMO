package ru.ifmo.ivshin.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Implementor implements Impler {

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (root == null || token == null) {
            throw new ImplerException("Null arguments in implement() method");
        }
        if (token.isPrimitive() || token.isArray() || Modifier.isFinal(token.getModifiers()) || token == Enum.class) {
            throw new ImplerException("Incorrect class token");
        }

        root = root
                .resolve(token.getPackage().getName()
                        .replace('.', File.separatorChar))
                .resolve(String.format("%s.%s", getClassName(token), "java"));

        if (root.getParent() != null) {
            try {
                Files.createDirectories(root.getParent());
            } catch (IOException e) {
                throw new ImplerException("Unable to create directories for outFile", e);
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(root)) {
            try {
                writer.write(getClassHead(token));
                if (!token.isInterface()) {
                    implementConstructors(token, writer);
                }
                implementAbstractMethods(token, writer);
                writer.write("}\n");
            } catch (IOException e) {
                throw new ImplerException("Unable to write to outFile", e);
            }
        } catch (IOException e) {
            throw new ImplerException("Unable to create outFile", e);
        }
    }

    private void implementAbstractMethods(Class<?> token, BufferedWriter writer) throws IOException {
        HashSet<ComparableMethod> methods = new HashSet<>();
        getAbstractMethods(token.getMethods(), methods);
        while (token != null) {
            getAbstractMethods(token.getDeclaredMethods(), methods);
            token = token.getSuperclass();
        }
        for (ComparableMethod method : methods) {
            writer.write(getExecutable(null, method.getMethod()).toString());
        }
    }

    private void getAbstractMethods(Method[] methods, Set<ComparableMethod> storage) {
        Arrays.stream(methods)
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .map(ComparableMethod::new)
                .collect(Collectors.toCollection(() -> storage));
    }

    private void implementConstructors(Class<?> token, BufferedWriter writer) throws IOException, ImplerException {
        Constructor<?>[] constructors = Arrays.stream(token.getDeclaredConstructors())
                .filter(constructor -> !Modifier.isPrivate(constructor.getModifiers()))
                .toArray(Constructor<?>[]::new);
        if (constructors.length == 0) {
            throw new ImplerException("No constructors in class");
        }
        for (Constructor<?> constructor : constructors) {
            writer.write(getExecutable(token, constructor).toString());
        }
    }

    private StringBuilder getExecutable(Class<?> token, Executable exec) {
        StringBuilder builder = new StringBuilder(getTabs(1));
        final int mods = exec.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.NATIVE & ~Modifier.TRANSIENT;
        builder.append(Modifier.toString(mods))
                .append(mods > 0 ? " " : "")
                .append(getReturnTypeAndName(token, exec))
                .append(getParams(exec, true))
                .append(getExceptions(exec)).append(" ")
                .append("{\n")
                .append(getTabs(2))
                .append(getBody(exec))
                .append(";\n")
                .append(getTabs(1))
                .append("}\n");
        return builder;
    }

    private StringBuilder getExceptions(Executable exec) {
        StringBuilder builder = new StringBuilder();
        Class<?>[] exceptions = exec.getExceptionTypes();
        if (exceptions.length > 0) {
            builder.append(" throws ");
        }
        builder.append(Arrays.stream(exceptions)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(",  "))
        );
        return builder;
    }

    private StringBuilder getTabs(int cnt) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(cnt));
        return builder;
    }

    private String getClassName(Class<?> token) {
        return token.getSimpleName() + "Impl";
    }

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

    private StringBuilder getPackage(Class<?> token) {
        StringBuilder builder = new StringBuilder();
        if (!token.getPackageName().equals("")) {
            builder.append("package ")
                    .append(token.getPackageName())
                    .append(";")
                    .append("\n");
        }
        builder.append("\n");
        return builder;
    }

    private String getClassHead(Class<?> token) {
        return String.format("%s public class %s %s %s {%n",
                getPackage(token), getClassName(token),
                (token.isInterface() ? "implements " : "extends "), token.getSimpleName());
    }

    private String getParam(Parameter param, boolean typeNeeded) {
        return String.format("%s%s", typeNeeded ? param.getType().getCanonicalName() + " " : "", param.getName());
    }

    private String getBody(Executable exec) {
        if (exec instanceof Method) {
            return "return" + getDefaultValue(((Method) exec).getReturnType());
        } else {
            return "super" + getParams(exec, false);
        }
    }

    private String getParams(Executable exec, boolean typedNeeded) {
        return Arrays.stream(exec.getParameters())
                .map(param -> getParam(param, typedNeeded))
                .collect(Collectors.joining(",  ", "(", ")"));
    }

    private String getReturnTypeAndName(Class<?> token, Executable exec) {
        if (exec instanceof Method) {
            Method tmp = (Method) exec;
            return tmp.getReturnType().getCanonicalName() + " " + tmp.getName();
        } else {
            return getClassName(token);
        }
    }

    private static class ComparableMethod {
        final private Method method;

        ComparableMethod(Method other) {
            method = other;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ComparableMethod other = (ComparableMethod) obj;
            return Arrays.equals(method.getParameterTypes(), other.method.getParameterTypes())
                    && method.getReturnType().equals(other.method.getReturnType())
                    && method.getName().equals(other.method.getName());
        }

        @Override
        public int hashCode() {
            return (Arrays.hashCode(method.getParameterTypes())
                    + method.getReturnType().hashCode())
                    + method.getName().hashCode();
        }

        Method getMethod() {
            return method;
        }
    }
}
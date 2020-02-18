package ru.ifmo.ivshin.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RecursiveWalk {

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Incorrect args. Expected: <inFileName> <outFileName>.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            String path;
            FileVisitor visitor = new FileVisitor(writer);
            while ((path = reader.readLine()) != null) {
                try {
                    Files.walkFileTree(Paths.get(path), visitor);
                } catch (InvalidPathException e) {
                    writer.write(String.format("%08x %s\n", 0, path));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static class FileVisitor extends SimpleFileVisitor<Path> {
        private static final int fnvFirst = 0x811c9dc5;
        private static final int fnvPrime = 0x01000193;
        private static final int shift = 0xff;
        private BufferedWriter writer;

        FileVisitor(BufferedWriter writer) {
            this.writer = writer;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            int h = fnvFirst;
            try (InputStream reader = Files.newInputStream(file)) {
                int c;
                while ((c = reader.read()) >= 0) {
                    h *= fnvPrime;
                    h ^= c & shift;
                }
            } catch (IOException e) {
                h = 0;
            }
            return writeData(h, file);
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return writeData(0, file);
        }

        private FileVisitResult writeData(int hash, Path file) throws IOException {
            writer.write(String.format("%08x %s\n", hash, file));
            return FileVisitResult.CONTINUE;
        }
    }
}

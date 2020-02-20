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

        Path inPath, outPath;

        try {
            inPath = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            System.out.println("Incorrect path to inFile: " + e.getMessage());
            return;
        }
        try {
            outPath = Paths.get(args[1]);
        } catch (InvalidPathException e) {
            System.out.println("Incorrect path to outFile: " + e.getMessage());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(inPath, StandardCharsets.UTF_8)) {
            try (BufferedWriter writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8)) {
                String path;
                FileVisitor visitor = new FileVisitor(writer);
                while ((path = reader.readLine()) != null) {
                    try {
                        Files.walkFileTree(Paths.get(path), visitor);
                    } catch (InvalidPathException e) {
                        writer.write(String.format("%08x %s%n", 0, path));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error with outFile: " + e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported encoding");
        } catch (IOException e) {
            System.out.println("Error with inFile: " + e.getMessage());
        }

    }

    private static class FileVisitor extends SimpleFileVisitor<Path> {
        private static final int fnvFirst = 0x811c9dc5;
        private static final int fnvPrime = 0x01000193;
        private static final int shift = 0xff;
        private byte[] buff = new byte[8192];
        private BufferedWriter writer;

        FileVisitor(BufferedWriter writer) {
            this.writer = writer;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            int h = fnvFirst;
            try (InputStream reader = Files.newInputStream(file)) {
                int c;
                while ((c = reader.read(buff)) >= 0) {
                    for (int i = 0; i < c; i++) {
                        h *= fnvPrime;
                        h ^= buff[i] & shift;
                    }
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

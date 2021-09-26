package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public abstract class TestWithResources {
    protected String readFromResources(String fileName, String fileExtension) throws Exception {
        //Path file = Paths.get(getClass().getResource(fileName + fileExtension).toURI());
        Path file = Paths.get("/Users/schrod/prog/ITMO/term_7/SE/Lab2/test/resources/" + fileName + fileExtension);
        return Files.lines(file).collect(Collectors.joining());
    }
}

package com.example.proiect_iot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class MyService {

    private final Path baseDir;

    public MyService(@Value("${file.base-dir}") String baseDir) throws IOException {
        this.baseDir = Paths.get(baseDir);
        if (!Files.exists(this.baseDir)) {
            Files.createDirectories(this.baseDir);
        }
    }

    public List<String> listFiles() throws IOException {
        try (var stream = Files.list(baseDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }

    public String readFile(String filename) throws IOException {
        Path path = baseDir.resolve(filename);
        return Files.readString(path);
    }

    public void createFile(String filename, String content) throws IOException {
        Path path = baseDir.resolve(filename);
        Files.writeString(path, content, StandardOpenOption.CREATE_NEW);
    }

    public String createFileWithAutoName(String content) throws IOException {
        Path tempFile = Files.createTempFile(baseDir, "file_", ".txt");
        Files.writeString(tempFile, content);
        return tempFile.getFileName().toString();
    }

    public void deleteFile(String filename) throws IOException {
        Path path = baseDir.resolve(filename);
        Files.deleteIfExists(path);
    }

    public void updateFile(String filename, String content) throws IOException {
        Path path = baseDir.resolve(filename);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Fisierul nu exista");
        }
        Files.writeString(path, content, StandardOpenOption.TRUNCATE_EXISTING);
    }
}

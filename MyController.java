package com.example.proiect_iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping
    public List<String> listFiles() throws IOException {
        return myService.listFiles();
    }

    @GetMapping("/{filename}")
    public String readFile(@PathVariable String filename) throws IOException {
        return myService.readFile(filename);
    }

    @PostMapping("/{filename}")
    public ResponseEntity<String> createFile(
            @PathVariable String filename,
            @RequestBody String content) throws IOException {
        myService.createFile(filename, content);
        return ResponseEntity.ok("Fișier creat");
    }

    @PostMapping
    public ResponseEntity<String> createFileWithAutoName(@RequestBody String content) throws IOException {
        String filename = myService.createFileWithAutoName(content);
        return ResponseEntity.ok("Success: " + filename);
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) throws IOException {
        myService.deleteFile(filename);
        return ResponseEntity.ok("Fisier sters");
    }

    @PutMapping("/{filename}")
    public ResponseEntity<String> updateFile(
            @PathVariable String filename,
            @RequestBody String content) throws IOException {
        myService.updateFile(filename, content);
        return ResponseEntity.ok("Fisier updatat");
    }
}
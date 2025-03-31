package com.singhmanikesh.securestash.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements FileProcessingService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String filepath = uploadDirectory + "/" + file.getOriginalFilename();
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path path = Paths.get(filepath);
        //Files.write(path, file.getBytes());
        try(InputStream inputStream = file.getInputStream() ){
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }

        return filepath;

    }

    @Override
    public Resource downloadFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDirectory, filename); // Construct the file path
            File file = filePath.toFile();

            if (file.exists()) {
                return new UrlResource(file.toURI());
            } else {
                System.out.println("Error: File not found - " + filename); // Print error message
            }
        } catch (MalformedURLException e) {
            System.out.println("Error: Malformed URL - " + e.getMessage());
        }
        return null;
    }

}

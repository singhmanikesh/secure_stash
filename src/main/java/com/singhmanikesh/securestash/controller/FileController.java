package com.singhmanikesh.securestash.controller;

import com.singhmanikesh.securestash.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private FileStorageService fileStorageService;
    public FileController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try{
            String filePath = fileStorageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + filePath);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }

    }

    @GetMapping(value = "/download/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadfile(@PathVariable(value = "name") String filename){
        Resource file = fileStorageService.downloadFile(filename);
        if(file == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: File not found - " + filename);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"" )
                    .body(file);

            //The Content-Disposition header will make the browser automatically prompt to download the file.
        }


    }


}

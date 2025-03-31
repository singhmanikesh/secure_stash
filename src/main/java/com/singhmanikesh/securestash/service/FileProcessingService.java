package com.singhmanikesh.securestash.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileProcessingService {
    String uploadFile(MultipartFile file) throws IOException;
    Resource downloadFile(String filename);
}

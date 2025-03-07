package com.example.spring.basicboardv2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    // C:\Users\hi-pc-999\Desktop\java\springboot\ uploads
    private final String UPLOADED_FOLDER = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "java" + File.separator + "springboot" + File.separator + "uploads" + File.separator;

    public String fileUpload(MultipartFile file) {
        // 파일 저장 로직
        try {
            byte[] bytes = file.getBytes(); // 받은 파일을 2진수로 저장
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());

            Files.write(path, bytes);

            return UPLOADED_FOLDER + file.getOriginalFilename(); // DB에 경로를 저장하기 위해
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

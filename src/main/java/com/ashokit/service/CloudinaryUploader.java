package com.ashokit.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryUploader {
    String uploadFile(MultipartFile file);
}

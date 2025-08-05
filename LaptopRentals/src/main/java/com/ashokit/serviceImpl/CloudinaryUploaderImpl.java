package com.ashokit.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashokit.cloudinarySignatureUtil.CloudinarySignatureUtil;
import com.ashokit.securityconfig.CloudinaryConfig;
import com.ashokit.service.CloudinaryUploader;
import com.cloudinary.Cloudinary;

@Service
public class CloudinaryUploaderImpl implements CloudinaryUploader {

    private final Cloudinary cloudinary;
    private final CloudinaryConfig cloudinaryConfig;

    public CloudinaryUploaderImpl(Cloudinary cloudinary, CloudinaryConfig cloudinaryConfig) {
        this.cloudinary = cloudinary;
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            System.out.println("Uploading: " + file.getOriginalFilename());

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), new HashMap<>());
            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cloudinary upload failed", e);
        }
    }


}




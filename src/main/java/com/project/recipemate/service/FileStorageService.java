package com.project.recipemate.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageService {
    private final Cloudinary cloudinary;

    public FileStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file)throws IOException {
            Map uploadResult=cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder","recipemate/photos")
            );
            return  uploadResult.get("secure_url").toString();
    }
}

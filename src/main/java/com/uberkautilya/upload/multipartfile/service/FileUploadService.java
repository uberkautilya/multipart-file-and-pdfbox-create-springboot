package com.uberkautilya.upload.multipartfile.service;

import com.uberkautilya.upload.multipartfile.model.Response;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public abstract Response uploadZipFile(MultipartFile file, String userId);
}

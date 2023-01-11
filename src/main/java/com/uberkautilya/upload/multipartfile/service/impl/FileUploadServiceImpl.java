package com.uberkautilya.upload.multipartfile.service.impl;

import com.uberkautilya.upload.multipartfile.model.Response;
import com.uberkautilya.upload.multipartfile.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public Response uploadZipFile(MultipartFile file, String userId) {
        String fileName = file.getOriginalFilename();
        Path parentPath = Paths.get("C:/Users/Mithun/IdeaProjects/multipart-file" + "/" + fileName);
        Path zipPath = Paths.get(parentPath + "/" + createUniqueName() + ".zip");
        if (null == zipPath) {
            return null;
        }
        try {
            byte[] byteArray = file.getBytes();
            Files.createDirectories(parentPath);
            Files.write(zipPath, byteArray);
            return new Response(zipPath.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createUniqueName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        return simpleDateFormat.format(new Date());
    }

}

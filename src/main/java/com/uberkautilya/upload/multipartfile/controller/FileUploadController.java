package com.uberkautilya.upload.multipartfile.controller;

import com.uberkautilya.upload.multipartfile.model.Response;
import com.uberkautilya.upload.multipartfile.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("uploadZipFile")
    public Response uploadZipFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) String userId) {
        Response response = fileUploadService.uploadZipFile(file, userId);
        return response;
    }
}

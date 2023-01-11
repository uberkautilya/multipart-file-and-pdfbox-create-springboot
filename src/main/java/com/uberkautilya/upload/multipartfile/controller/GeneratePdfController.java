package com.uberkautilya.upload.multipartfile.controller;

import com.uberkautilya.upload.multipartfile.model.Payload;
import com.uberkautilya.upload.multipartfile.model.PdfResponse;
import com.uberkautilya.upload.multipartfile.service.GeneratePdfService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/")
public class GeneratePdfController {
    @Autowired
    private GeneratePdfService generatePdfService;

    @PostMapping("downloadGeneratedPdf")
    public void downloadGeneratedPdf(@RequestBody(required = false) Payload payload, HttpServletRequest request, HttpServletResponse response) {
        PdfResponse pdfResponse = generatePdfService.getPdf(payload);
        response.setContentType(pdfResponse.getContentType());
        response.addHeader("Content-Disposition", "attachment; filename=" + pdfResponse.getFileName());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(pdfResponse.getFileData());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Stream write exception");
            throw new RuntimeException(e);
        }
    }

    @PostMapping("previewGeneratedPdf")
    public void previewGeneratedPdf(@RequestBody(required = false) Payload payload, HttpServletRequest request, HttpServletResponse response) {
        PdfResponse pdfResponse = generatePdfService.getPdf(payload);
        response.setContentType(pdfResponse.getContentType());
        response.addHeader("Content-Disposition", "attachment; filename=" + pdfResponse.getFileName());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            //Have to create the pdf before flushing for preview using Base64 encoder?
            outputStream.write(Base64.getEncoder().encode(pdfResponse.getFileData()));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Stream write exception");
            throw new RuntimeException(e);
        }
    }
}

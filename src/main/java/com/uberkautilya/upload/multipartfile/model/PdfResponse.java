package com.uberkautilya.upload.multipartfile.model;

import java.util.Arrays;

public class PdfResponse {
    String fileName;
    byte[] fileData;
    String fileType;
    String contentType;

    @Override
    public String toString() {
        return "PdfResponse{" +
                "fileName='" + fileName + '\'' +
                ", fileData=" + Arrays.toString(fileData) +
                ", fileType='" + fileType + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

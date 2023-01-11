package com.uberkautilya.upload.multipartfile.service;

import com.uberkautilya.upload.multipartfile.model.Payload;
import com.uberkautilya.upload.multipartfile.model.PdfResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface GeneratePdfService {
    PdfResponse getPdf(Payload payload);
}

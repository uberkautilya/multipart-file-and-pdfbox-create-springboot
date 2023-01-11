package com.uberkautilya.upload.multipartfile.service.impl;

import com.uberkautilya.upload.multipartfile.model.Payload;
import com.uberkautilya.upload.multipartfile.model.PdfResponse;
import com.uberkautilya.upload.multipartfile.service.GeneratePdfService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GeneratePdfServiceImpl implements GeneratePdfService {
    public static void main(String[] args) throws IOException {
        new GeneratePdfServiceImpl().getPdf(null);
    }

    @Override
    public PdfResponse getPdf(Payload payload) {
        PdfResponse pdfResponse = new PdfResponse();
        Map<String, String> valueMap = new LinkedHashMap<>();
        valueMap.put("Key 101", "Value 1001");
        try {
            String fileName = "FileName.pdf";
            byte[] pdfFileData = generatePdfData(valueMap, "Test", fileName);
            pdfResponse.setFileName(fileName);
            pdfResponse.setFileData(pdfFileData);
            pdfResponse = setContentType(pdfResponse, fileName);
        } catch (IOException e) {
            System.out.println("Exception while creating the pdf");
            throw new RuntimeException(e);
        }
        return pdfResponse;
    }

    private PdfResponse setContentType(PdfResponse pdfResponse, String fileName) {
        if (fileName.endsWith(".pdf")) {
            pdfResponse.setFileType(".pdf");
            pdfResponse.setContentType("application/pdf");
        } else if (fileName.endsWith(".png")) {
            pdfResponse.setFileType(".png");
            pdfResponse.setContentType("image/png");
        } else if (fileName.endsWith(".docx")) {
            pdfResponse.setFileType(".docx");
            pdfResponse.setContentType("application/vnd.openxmlformats-officedocument.workprocessingml.document");
        }
        return pdfResponse;
    }

    private byte[] generatePdfData(Map<String, String> valueMap, String heading, String fileName) throws IOException {
        float headingFontSize = 25.0f;
        float fontSize = headingFontSize / 2;
        float leadingSpaceToNextLine = 2.5f * fontSize;
        float topMargin = 55.0f;
        float verticalOffsetBody = 110.0f;
        float leftMargin = 70.0f;

        PDDocument document = new PDDocument();
        PDPage pdPage = new PDPage(PDRectangle.A4);
        PDFont font = PDType1Font.HELVETICA;
        float pageWidth = pdPage.getTrimBox().getWidth();
        float pageHeight = pdPage.getTrimBox().getHeight();
        document.addPage(pdPage);

        float maxLabelWidth = 0.0f;
        ArrayList<String[]> dataArray = new ArrayList<>();
        for (String key : valueMap.keySet()) {
            dataArray.add(new String[]{key, valueMap.get(key)});
            maxLabelWidth = font.getStringWidth(key) > maxLabelWidth ? font.getStringWidth(key + ":") : maxLabelWidth;
        }
        maxLabelWidth = maxLabelWidth / 1000 * fontSize + leftMargin;
        PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);

        contentStream.setFont(font, headingFontSize);
        contentStream.setNonStrokingColor(Color.BLACK);
        printHeadingForPdf(heading, headingFontSize, topMargin, font, pageWidth, pageHeight, contentStream);

        contentStream.setFont(font, fontSize);
        contentStream.setLeading(leadingSpaceToNextLine);
        printLabelColumnInPdf(verticalOffsetBody, pageHeight, leftMargin, dataArray, contentStream);
        printColonColumnInPdf(verticalOffsetBody, pageHeight, maxLabelWidth, dataArray, contentStream);

        //Move text by 15 units after the colon
        maxLabelWidth += 15;
        printValueColumnInPdf(verticalOffsetBody, pageHeight, maxLabelWidth, dataArray, contentStream);
        contentStream.close();

        ByteArrayOutputStream outputStream = savePdfOutputStream(fileName, document);
        return outputStream.toByteArray();
    }

    private static ByteArrayOutputStream savePdfOutputStream(String fileName, PDDocument document) throws IOException {
        //To save pdf
        document.save(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        return outputStream;
    }

    private static void printHeadingForPdf(String heading, float headingFontSize, float topMargin, PDFont font, float pageWidth, float pageHeight, PDPageContentStream contentStream) throws IOException {
        //Print heading
        contentStream.beginText();
        float headingWidth = font.getStringWidth(heading) / 1000 * headingFontSize;
        contentStream.newLineAtOffset((pageWidth - headingWidth) / 2, pageHeight - topMargin);
        contentStream.showText(heading);
        contentStream.endText();
    }

    private static void printValueColumnInPdf(float verticalOffsetBody, float pageHeight, float maxHeadingWidth, ArrayList<String[]> dataArray, PDPageContentStream contentStream) throws IOException {
        //Print values against the labels on the left
        contentStream.beginText();
        contentStream.newLineAtOffset(maxHeadingWidth, pageHeight - verticalOffsetBody);
        for (String values[] : dataArray) {
            contentStream.showText(null != values[1] ? values[1] : "");
            contentStream.newLine();
        }
        contentStream.endText();
    }

    private static void printColonColumnInPdf(float verticalOffsetBody, float pageHeight, float maxHeadingWidth, ArrayList<String[]> dataArray, PDPageContentStream contentStream) throws IOException {
        //Print colons
        contentStream.beginText();
        contentStream.newLineAtOffset(maxHeadingWidth, pageHeight - verticalOffsetBody);
        for (String values[] : dataArray) {
            contentStream.showText(":");
            contentStream.newLine();
        }
        contentStream.endText();
    }

    private static void printLabelColumnInPdf(float verticalOffsetBody, float pageHeight, float leftMargin, ArrayList<String[]> dataArray, PDPageContentStream contentStream) throws IOException {
        //Print labels of the dataArray
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, pageHeight - verticalOffsetBody);
        for (String values[] : dataArray) {
            contentStream.showText(null != values[0] ? values[0] : "");
            contentStream.newLine();
        }
        contentStream.endText();
    }
}

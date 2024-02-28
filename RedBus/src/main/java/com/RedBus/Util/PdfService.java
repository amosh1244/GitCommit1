package com.RedBus.Util;

import com.RedBus.User.PayLoad.BookingDetailsDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(BookingDetailsDto bookingDetails) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfWriter pdfWriter = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter)) {

            Document document = new Document(pdfDocument);

            // Add details to the PDF
            document.add(new Paragraph("Booking ID: " + bookingDetails.getBookingId()));
            document.add(new Paragraph("Bus Company: " + bookingDetails.getBusCompany()));
            document.add(new Paragraph("To City: " + bookingDetails.getToCity()));
            document.add(new Paragraph("From City: " + bookingDetails.getFromCity()));
            document.add(new Paragraph("First Name: " + bookingDetails.getFirstName()));
            document.add(new Paragraph("Last Name: " + bookingDetails.getLastName()));
            document.add(new Paragraph("Email: " + bookingDetails.getEmail()));
            document.add(new Paragraph("Mobile: " + bookingDetails.getMobile()));
            document.add(new Paragraph("Price: " + bookingDetails.getPrice()));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
}

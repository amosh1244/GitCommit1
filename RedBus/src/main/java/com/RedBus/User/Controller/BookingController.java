package com.RedBus.User.Controller;
import com.RedBus.User.PayLoad.BookingDetailsDto;
import com.RedBus.User.PayLoad.PassengerDetails;
import com.RedBus.User.Service.BookingService;
import com.RedBus.Util.EmailService;
import com.RedBus.Util.PdfService;  // Import your PDF generation service
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    private EmailService emailService;
    private PdfService pdfGenerationService;  // Inject your PDF generation service

    public BookingController(BookingService bookingService, EmailService emailService, PdfService pdfGenerationService) {
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.pdfGenerationService = pdfGenerationService;
    }

    // http://localhost:8080/api/bookings?busId=&ticketId=
    @PostMapping
    public ResponseEntity<BookingDetailsDto> bookBus(
            @RequestParam("busId") String busId,
            @RequestParam("ticketId") String ticketId,
            @RequestBody PassengerDetails passengerDetails
    ) {
        BookingDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);

        if (booking != null) {

            // Generate PDF and send as an attachment
            byte[] pdfBytes = pdfGenerationService.generatePdf(booking);

            sendPdfAsAttachment(passengerDetails.getEmail(), pdfBytes);
        }

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    private void sendPdfAsAttachment(String recipientEmail, byte[] pdfBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "booking-details.pdf");

        emailService.sendEmailWithAttachment(
                recipientEmail,
                "Booking Details - PDF Attachment",
                "Please find attached booking details.",
                pdfBytes,
                String.valueOf(headers)
        );
    }
}

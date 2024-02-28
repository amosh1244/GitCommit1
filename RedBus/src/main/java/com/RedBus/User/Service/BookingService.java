package com.RedBus.User.Service;

import com.RedBus.BusOperator.Entity.BusOperator;
import com.RedBus.BusOperator.Entity.TicketCost;
import com.RedBus.BusOperator.Repository.BusOperatorRepository;
import com.RedBus.BusOperator.Repository.TicketCostRepository;
import com.RedBus.User.Entity.Booking;
import com.RedBus.User.PayLoad.BookingDetailsDto;
import com.RedBus.User.PayLoad.PassengerDetails;
import com.RedBus.User.Repository.BookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {


    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    private TicketCostRepository ticketCostRepository;

    private BookingRepository bookingRepository;

    private BusOperatorRepository busOperatorRepository;


    public BookingService(TicketCostRepository ticketCostRepository, BookingRepository bookingRepository, BusOperatorRepository busOperatorRepository) {
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
        this.busOperatorRepository = busOperatorRepository;
    }

    public BookingDetailsDto createBooking(
            String busId,
            String ticketId,
            PassengerDetails passengerDetails
    ){
        BusOperator bus =   busOperatorRepository.findById(busId).get();
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).get();

        String paymentIntent = processPayment((int) ticketCost.getCost());

        if(paymentIntent!=null) {

            Booking booking = new Booking();
            String BookingId = UUID.randomUUID().toString();
            booking.setBusId(busId);
            booking.setTicketId(ticketId);
            booking.setBookingId(BookingId);
            booking.setBusCompany(bus.getBusOperatorCompanyName());
            booking.setToCity(bus.getArrivalCity());
            booking.setFromCity(bus.getDepartureCity());
            booking.setPrice(ticketCost.getCost());
            booking.setFirstName(passengerDetails.getFirstName());
            booking.setLastName(passengerDetails.getLastName());
            booking.setEmail(passengerDetails.getEmail());
            booking.setMobile(passengerDetails.getMobile());

            Booking ticketCreatedDetails = bookingRepository.save(booking);

            BookingDetailsDto dto = new BookingDetailsDto();
            dto.setFromCity(ticketCreatedDetails.getFromCity());
            dto.setToCity(ticketCreatedDetails.getToCity());
            dto.setBusCompany(ticketCreatedDetails.getBusCompany());
            dto.setBookingId(ticketCreatedDetails.getBookingId());
            dto.setFirstName(ticketCreatedDetails.getFirstName());
            dto.setLastName(ticketCreatedDetails.getLastName());
            dto.setPrice(ticketCreatedDetails.getPrice());
            dto.setEmail(ticketCreatedDetails.getEmail());
            dto.setMobile(ticketCreatedDetails.getMobile());
            dto.setBusCompany(ticketCreatedDetails.getBusCompany());
            dto.setMessage("Booking Conform!");

            return dto;
        }else {
            System.out.println("Error!!");
        }

        return null;
    }


    public String processPayment(Integer amount) {
        Stripe.apiKey = stripeApiKey;

        try {

            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (amount * 100)) // Amount in cents
                            .setCurrency("usd")
                            .build()
            );

            // Payment success logic
            return "Payment successful. Charge ID: " + charge.getId();
        } catch (StripeException e) {
            // Payment failure logic
            return "Payment failed. Error: " + e.getMessage();
        }
    }



}

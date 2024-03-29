package com.RedBus.User.PayLoad;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDto {


    private String bookingId;

    private String busCompany;

    private String toCity;

    private String fromCity;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private double price;

    private String message;



}

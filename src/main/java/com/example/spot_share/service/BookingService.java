package com.example.spot_share.service;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.util.dto.BookingDetails;
import com.example.spot_share.util.dto.BookingWithoutParker;

import java.util.List;
import java.util.UUID;

public interface BookingService {

    Booking saveBooking(UUID parkingId);

    List<BookingWithoutParker> getBookingWithoutParker(int pageNumber, int pageSize);

    List<BookingDetails> getBookingDetails(int pageNumber, int pageSize, UUID parkingId);

    boolean cancelBooking(UUID bookingId);

    BookingDetails getBookingDetails(UUID bookingId);
}

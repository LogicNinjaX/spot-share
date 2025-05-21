package com.example.spot_share.service;

import com.example.spot_share.entity.Booking;

import java.util.UUID;

public interface BookingService {

    Booking saveBooking(UUID parkingId);
}

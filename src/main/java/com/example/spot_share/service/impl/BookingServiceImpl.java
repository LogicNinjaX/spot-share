package com.example.spot_share.service.impl;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.entity.User;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.repository.BookingRepository;
import com.example.spot_share.repository.ParkingSpotRepository;
import com.example.spot_share.security.SecurityUtils;
import com.example.spot_share.service.BookingService;
import com.example.spot_share.util.exception.ParkingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final EntityManager entityManager;
    private final BookingRepository bookingRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public BookingServiceImpl(EntityManager entityManager, BookingRepository bookingRepository, ParkingSpotRepository parkingSpotRepository) {
        this.entityManager = entityManager;
        this.bookingRepository = bookingRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    @Transactional
    public Booking saveBooking(UUID parkingId) {

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = entityManager.getReference(User.class, currentUserId);

        ParkingSpot parkingSpot = parkingSpotRepository.findByIdForUpdate(parkingId)
                .orElseThrow(() -> new ParkingException("parking space not found"));

        if (parkingSpot.getParkingStatus() != ParkingStatus.AVAILABLE){
            throw new ParkingException("parking space not available");
        }

        parkingSpot.setParkingStatus(ParkingStatus.OCCUPIED);

        Booking booking = new Booking(user, parkingSpot);

        return bookingRepository.save(booking);
    }
}

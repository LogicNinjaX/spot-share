package com.example.spot_share.service.impl;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.entity.User;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.repository.BookingRepository;
import com.example.spot_share.repository.ParkingSpotRepository;
import com.example.spot_share.service.BookingService;
import com.example.spot_share.util.dto.BookingDetails;
import com.example.spot_share.util.dto.BookingWithoutParker;
import com.example.spot_share.util.dto.ParkingDetails;
import com.example.spot_share.util.exception.BookingException;
import com.example.spot_share.util.exception.ParkingException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Booking saveBooking(UUID userId, UUID parkingId) {

        User user = entityManager.getReference(User.class, userId);

        ParkingSpot parkingSpot = parkingSpotRepository.findByIdForUpdate(parkingId)
                .orElseThrow(() -> new ParkingException("parking space not found"));

        if (parkingSpot.getParkingStatus() != ParkingStatus.AVAILABLE){
            throw new ParkingException("parking space not available");
        }

        parkingSpot.setParkingStatus(ParkingStatus.OCCUPIED);

        Booking booking = new Booking(user, parkingSpot);

        return bookingRepository.save(booking);
    }

    @Override
    public List<BookingWithoutParker> getBookingWithoutParker(UUID userId, int pageNumber, int pageSize) {
        return bookingRepository.getBookingWithoutParker(userId, PageRequest.of(pageNumber-1, pageSize)).toList();
    }

    @Override
    public List<BookingDetails> getBookingDetails(int pageNumber, int pageSize, UUID parkingId) {
        return bookingRepository.getBookingDetails(parkingId, PageRequest.of(pageNumber-1, pageSize)).toList();
    }

    @Override
    @Transactional
    public boolean cancelBooking(UUID bookingId) {
        ParkingDetails parkingDetails = parkingSpotRepository.getParkingDetailsByBookingId(bookingId)
                .orElseThrow(() -> new ParkingException("parking space not found"));

        try{
        int deactivated = bookingRepository.deactivateBooking(bookingId);
        int updated = parkingSpotRepository.updateParkingStatus(parkingDetails.parkingId(), ParkingStatus.AVAILABLE);

        if (deactivated <= 0) {
            throw new BookingException("Failed to deactivate booking");
        }

        if (updated <= 0) {
            throw new ParkingException("Failed to update parking spot status");
        }

        return true;

        } catch (DataAccessException dae) {
            throw new BookingException("Database error during booking cancellation: "+dae);
        } catch (RuntimeException ex) {
            throw new BookingException("Unexpected error during booking cancellation: "+ ex);
        }
    }

    @Override
    public BookingDetails getBookingDetails(UUID bookingId) {
        return bookingRepository.getBookingDetails(bookingId).orElseThrow(() -> new BookingException("booking details not found"));
    }
}

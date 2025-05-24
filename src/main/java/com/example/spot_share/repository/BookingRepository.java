package com.example.spot_share.repository;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.util.dto.BookingDetails;
import com.example.spot_share.util.dto.BookingWithoutParker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {


    @Query("SELECT new com.example.spot_share.util.dto.BookingWithoutParker(b.bookingId, b.bookedAt, b.isActive, b.parkingSpot.location, b.parkingSpot.pricePerHour, b.parkingSpot.owner.username) FROM Booking b WHERE b.parker.userId = :userId")
    Page<BookingWithoutParker> getBookingWithoutParker(@Param("userId") UUID userId, Pageable pageable);


    @Query("SELECT new com.example.spot_share.util.dto.BookingDetails(b.bookingId, b.parker.username, b.parkingSpot.parkingId, b.parkingSpot.location, b.parkingSpot.vehicleType, b.bookedAt, b.isActive) FROM Booking b WHERE b.parkingSpot.parkingId = :parkingId")
    Page<BookingDetails> getBookingDetails(@Param("parkingId") UUID parkingId, Pageable pageable);

    @Modifying
    @Query("UPDATE Booking b SET b.isActive = false WHERE b.bookingId = :bookingId")
    int deactivateBooking(@Param("bookingId") UUID bookingId);

    @Query("SELECT new com.example.spot_share.util.dto.BookingDetails(b.bookingId, b.parker.username, b.parkingSpot.parkingId, b.parkingSpot.location, b.parkingSpot.vehicleType, b.bookedAt, b.isActive) FROM Booking b WHERE b.bookingId = :bookingId")
    Optional<BookingDetails> getBookingDetails(@Param("bookingId") UUID bookingId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.isActive = :status AND b.parker.userId = :userId AND b.parkingSpot.parkingId = :parkingId")
    long isBookingActive(@Param("status") boolean status, @Param("userId") UUID userId, @Param("parkingId") UUID parkingId);

    @Query("""
    SELECT b FROM Booking b
    JOIN FETCH b.parker
    JOIN FETCH b.parkingSpot
    WHERE b.bookingId = :bookingId
    """)
    Optional<Booking> findWithUserById(UUID bookingId);

}

package com.example.spot_share.repository;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.util.dto.BookingWithoutParker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {


    @Query("SELECT new com.example.spot_share.util.dto.BookingWithoutParker(b.bookingId, b.bookedAt, b.isActive, b.parkingSpot.location, b.parkingSpot.pricePerHour, b.parkingSpot.owner.username) FROM Booking b WHERE b.parker.userId = :userId")
    Page<BookingWithoutParker> getBookingWithoutParker(@Param("userId") UUID userId, Pageable pageable);


}

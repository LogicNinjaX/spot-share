package com.example.spot_share.repository;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.util.dto.ParkingDetails;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import jakarta.persistence.LockModeType;
import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

    @Query("SELECT new com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings(p.parkingId, p.location, p.pricePerHour, p.vehicleType, p.parkingStatus, p.createdAt, p.updatedAt)" +
            "FROM ParkingSpot p WHERE p.owner.userId = :userId")
    Page<ParkingSpotDtoWithoutBookings> getParkingList(@Param("userId") UUID userId, Pageable pageable);

    @EntityGraph(attributePaths = {})
    @Query("SELECT p FROM ParkingSpot p WHERE p.parkingId = :parkingId")
    Optional<ParkingSpot> getParkingSpot(@Param("parkingId") UUID parkingId);// // No associations fetched

    @Query("SELECT new com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings(p.parkingId, p.location, p.pricePerHour, p.vehicleType, p.parkingStatus, p.createdAt, p.updatedAt)" +
            "FROM ParkingSpot p WHERE p.parkingStatus = :parkingStatus")
    Page<ParkingSpotDtoWithoutBookings> getParkingListByStatus(@Param("parkingStatus") ParkingStatus status, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ParkingSpot p WHERE p.parkingId = :parkingId")
    Optional<ParkingSpot> findByIdForUpdate(@Param("parkingId") UUID parkingId);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.parkingStatus = :parkingStatus WHERE p.parkingId = :parkingId")
    int updateParkingStatus(@Param("parkingId") UUID parkingId, @Param("parkingStatus") ParkingStatus parkingStatus);

    @Query("SELECT new com.example.spot_share.util.dto.ParkingDetails(p.parkingId, p.location, p.pricePerHour, p.vehicleType, p.parkingStatus, p.createdAt, p.updatedAt) FROM ParkingSpot p WHERE p.bookings.bookingId = :bookingId")
    Optional<ParkingDetails> getParkingDetailsByBookingId(@Param("bookingId") UUID bookingId);
}

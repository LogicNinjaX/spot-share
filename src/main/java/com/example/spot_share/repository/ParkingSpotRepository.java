package com.example.spot_share.repository;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}

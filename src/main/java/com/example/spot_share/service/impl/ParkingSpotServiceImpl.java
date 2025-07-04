package com.example.spot_share.service.impl;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.entity.User;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.repository.ParkingSpotRepository;
import com.example.spot_share.service.ParkingSpotService;
import com.example.spot_share.util.api_response.UpdateParkingSpotRequest;
import com.example.spot_share.util.dto.ParkingSpotDto;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import com.example.spot_share.util.exception.ParkingException;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private ModelMapper modelMapper;
    private ParkingSpotRepository parkingSpotRepository;
    private EntityManager entityManager;

    public ParkingSpotServiceImpl(ModelMapper modelMapper, ParkingSpotRepository parkingSpotRepository, EntityManager entityManager) {
        this.modelMapper = modelMapper;
        this.parkingSpotRepository = parkingSpotRepository;
        this.entityManager = entityManager;
    }

    @Override
    public ParkingSpot saveParkingSpot(UUID userId, ParkingSpotDto parkingSpotDto) {
        User currentUser = entityManager.getReference(User.class, userId); // owner

        ParkingSpot parkingSpot = modelMapper.map(parkingSpotDto, ParkingSpot.class);

        parkingSpot.setOwner(currentUser);

        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    public List<ParkingSpotDtoWithoutBookings> getParkingList(UUID userId, int pageNumber, int pageSize) {
        return parkingSpotRepository.getParkingList(userId, PageRequest.of(pageNumber-1, pageSize)).stream().toList();
    }

    @Override
    public ParkingSpotDto updateParkingSpot(UUID parkingId, UpdateParkingSpotRequest request) { // for complete

        ParkingSpot currentParkingSpot = parkingSpotRepository.getParkingSpot(parkingId).map(parkingSpot -> {
            parkingSpot.setLocation(request.getLocation());
            parkingSpot.setPricePerHour(request.getPricePerHour());
            parkingSpot.setVehicleType(request.getVehicleType());
            parkingSpot.setParkingStatus(request.getParkingStatus());
            return parkingSpot;
        }).orElseThrow(() -> new ParkingException("parking spot not found with id:"+parkingId));

        ParkingSpot updatedParkingSpot = parkingSpotRepository.save(currentParkingSpot); // after mapping

        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setParkingId(updatedParkingSpot.getParkingId());
        parkingSpotDto.setLocation(updatedParkingSpot.getLocation());
        parkingSpotDto.setPricePerHour(updatedParkingSpot.getPricePerHour());
        parkingSpotDto.setVehicleType(updatedParkingSpot.getVehicleType());
        parkingSpotDto.setParkingStatus(updatedParkingSpot.getParkingStatus());
        parkingSpotDto.setCreatedAt(updatedParkingSpot.getCreatedAt());
        parkingSpotDto.setUpdatedAt(updatedParkingSpot.getUpdatedAt());
        return parkingSpotDto;
    }

    @Override
    public void deleteParking(UUID parkingId) {
        parkingSpotRepository.deleteById(parkingId);
    }

    @Override
    public List<ParkingSpotDtoWithoutBookings> getParkingListByStatus(int pageNumber, int pageSize, ParkingStatus status) {
        return parkingSpotRepository.getParkingListByStatus(status, PageRequest.of(pageNumber-1, pageSize)).toList();
    }

}

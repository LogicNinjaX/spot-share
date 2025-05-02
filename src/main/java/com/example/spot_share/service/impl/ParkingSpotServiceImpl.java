package com.example.spot_share.service.impl;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.entity.User;
import com.example.spot_share.repository.ParkingSpotRepository;
import com.example.spot_share.security.SecurityUtils;
import com.example.spot_share.service.ParkingSpotService;
import com.example.spot_share.util.dto.ParkingSpotDto;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public ParkingSpot saveParkingSpot(ParkingSpotDto parkingSpotDto) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User currentUser = entityManager.getReference(User.class, currentUserId); // owner

        ParkingSpot parkingSpot = modelMapper.map(parkingSpotDto, ParkingSpot.class);

        parkingSpot.setOwner(currentUser);

        return parkingSpotRepository.save(parkingSpot);
    }
}

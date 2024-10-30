package com.boulderit.service;

import com.boulderit.exception.ResourceNotFoundException;
import com.boulderit.model.ParkingSpot;
import com.boulderit.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService{

    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingServiceImpl(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    @Override
    public ParkingSpot findById(String Id) {
        return null;
    }

    @Override
    public ParkingSpot save(ParkingSpot parkingSpot) {
        return null;
    }

    @Override
    public ParkingSpot update(ParkingSpot parkingSpot, String id) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        ParkingSpot existingSpot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking spot not found with id: " + id));

        if (parkingSpot == null) {
            throw new IllegalArgumentException("Parking spot data cannot be null");
        }

        try {
            parkingSpot.setId(id);
            return parkingSpotRepository.save(parkingSpot);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Invalid parking spot data");
        }



    }

    @Override
    public void delete(String Id) {

    }
}
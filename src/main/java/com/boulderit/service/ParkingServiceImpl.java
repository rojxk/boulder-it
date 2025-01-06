package com.boulderit.service;

import com.boulderit.model.ParkingSpot;
import com.boulderit.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    public ParkingSpot update(ParkingSpot parkingSpot, String id) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        ParkingSpot existingSpot = parkingSpotRepository.findById(id)
                .orElseThrow();

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
    @Transactional
    public void delete(String Id) {
        parkingSpotRepository.deleteByParkingId(Id);
    }

    @Override
    public ParkingSpot findByAreaId(String Id) {
        try {
            return parkingSpotRepository.findByAreaId(Id);
        } catch (Exception e) {
            return null;
        }
    }
}

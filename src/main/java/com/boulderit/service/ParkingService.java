package com.boulderit.service;

import com.boulderit.model.ParkingSpot;

import java.util.List;

public interface ParkingService {
    List<ParkingSpot> findAll();
    ParkingSpot findById(String Id);
    ParkingSpot save(ParkingSpot parkingSpot);
    ParkingSpot update(ParkingSpot parkingSpot, String id);
    void delete(String Id);
}

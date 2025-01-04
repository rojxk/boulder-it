package com.boulderit.service;

import com.boulderit.model.Area;
import com.boulderit.model.ParkingSpot;

import java.util.List;

public interface AreaService {
    List<Area> findAll();
    Area findById(String id);
    Area save(Area area);
    List<Area> findNearby(double lat, double lon, double maxDistance);
    Area addParkingSpot(String areaId, ParkingSpot parkingSpot);

}

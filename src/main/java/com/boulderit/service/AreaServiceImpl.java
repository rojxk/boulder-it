package com.boulderit.service;

import com.boulderit.model.Area;
import com.boulderit.model.ParkingSpot;
import com.boulderit.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;

import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private AreaRepository areaRepository;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    @Override
    public Area findById(String id) {

        return areaRepository.findById(id).orElseThrow();

    }

    @Transactional
    @Override
    public Area save(Area area) {
        return areaRepository.save(area);
    }

    @Override
    public List<Area> findNearby(double lat, double lon, double maxDistance) {
        GeoJsonPoint point = new GeoJsonPoint(lon, lat);
        Distance distance = new Distance(maxDistance, Metrics.KILOMETERS);
        return areaRepository.findByCoordinatesNear(point, distance);
    }

    public Area addParkingSpot(String areaId, ParkingSpot parkingSpot) {
        Area area = findById(areaId);
        area.addParkingSpot(parkingSpot);
        area.setUpdatedAt(new Date());
        return areaRepository.save(area);
    }
}

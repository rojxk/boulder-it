package com.boulderit.controller;

import com.boulderit.model.ParkingSpot;
import com.boulderit.service.AreaService;
import com.boulderit.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/parking")
public class ParkingController {
    private ParkingService parkingService;
    private AreaService areaService;

    @Autowired
    public ParkingController(ParkingService parkingService, AreaService areaService) {
        this.parkingService = parkingService;
        this.areaService = areaService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpots() {
        return ResponseEntity.ok(parkingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable String id) {
        return ResponseEntity.ok(parkingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> createParkingSpot(@RequestBody ParkingSpot parkingSpot, @RequestParam String areaId) {
        parkingSpot.setCreatedAt(new Date());
        parkingSpot.setUpdatedAt(new Date());
        ParkingSpot saved = parkingService.save(parkingSpot);
        areaService.addParkingSpot(areaId, saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ParkingSpot> updateParkingSpot(@PathVariable String id, @RequestBody ParkingSpot parkingSpot) {
        return ResponseEntity.ok(parkingService.update(parkingSpot, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpot(@PathVariable String id, @RequestParam String areaId) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }



}

package com.boulderit.controller;

import com.boulderit.model.ParkingSpot;
import com.boulderit.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/parking")
public class ParkingController {
    private ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
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
    public  ResponseEntity<ParkingSpot> createParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        return ResponseEntity.ok(parkingService.save(parkingSpot));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ParkingSpot> updateParkingSpot(@PathVariable String id, @RequestBody ParkingSpot parkingSpot) {
        return ResponseEntity.ok(parkingService.update(parkingSpot, id));
    }



}

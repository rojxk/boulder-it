package com.boulderit.controller;

import com.boulderit.model.Area;
import com.boulderit.model.ParkingSpot;
import com.boulderit.model.Sector;
import com.boulderit.service.AreaService;
import com.boulderit.service.ParkingService;
import com.boulderit.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/areas")
public class AreaController {

    private AreaService areaService;
    private ParkingService parkingService;
    private SectorService sectorService;

    @Autowired
    public AreaController(AreaService areaService, ParkingService parkingService, SectorService sectorService){
        this.areaService = areaService;
        this.parkingService = parkingService;
        this.sectorService = sectorService;
    }

    @GetMapping
    public ResponseEntity<List<Area>> findAll(){
        return ResponseEntity.ok(areaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> findById(@PathVariable String id) {
        return ResponseEntity.ok(areaService.findById(id));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<Map<String, Object>> getFullAreaInfo(@PathVariable String id) {
        Area area = areaService.findById(id);
        List<Sector> sectors = sectorService.findByAreaId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("area", area);
        response.put("sectors", sectors != null ? sectors : Collections.emptyList());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/near")
    public ResponseEntity<List<Area>> findNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "10000") double maxDistance) {
        return ResponseEntity.ok(areaService.findNearby(lat, lon, maxDistance));
    }

    @PostMapping
    public ResponseEntity<Area> save(@RequestBody Area area) {
        return ResponseEntity.ok(areaService.save(area));
    }



}

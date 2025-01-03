package com.boulderit.controller;

import com.boulderit.model.Problem;
import com.boulderit.model.Sector;
import com.boulderit.service.ProblemService;
import com.boulderit.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    private ProblemService problemService;
    private SectorService sectorService;

    @Autowired
    public SectorController(ProblemService problemService, SectorService sectorService){
        this.problemService = problemService;
        this.sectorService = sectorService;
    }

    @GetMapping("/{sectorId}")
    public ResponseEntity<Sector> findById(@PathVariable String sectorId){
        return ResponseEntity.ok(sectorService.findById(sectorId));
    }

    @GetMapping("/{sectorId}/problems")
    public ResponseEntity<List<Problem>> getProblemsBySector(@PathVariable String sectorId) {
        return ResponseEntity.ok(problemService.findBySectorId(sectorId));
    }
}

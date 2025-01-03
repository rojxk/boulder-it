package com.boulderit.service;

import com.boulderit.model.Sector;
import com.boulderit.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SectorServiceImpl implements SectorService{

    private SectorRepository sectorRepository;

    @Autowired
    public SectorServiceImpl(SectorRepository sectorRepository){
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<Sector> findAll() {
        return sectorRepository.findAll();
    }

    @Override
    public Sector findById(String id) {
        return sectorRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Sector> findByAreaId(String id) {
        try {
            List<Sector> sectors = sectorRepository.findByAreaId(id);
            return sectors != null ? sectors : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

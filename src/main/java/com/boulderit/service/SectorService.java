package com.boulderit.service;

import com.boulderit.model.Sector;

import java.util.List;

public interface SectorService {
    List<Sector> findAll();
    Sector findById(String id);
    List<Sector> findByAreaId(String id);

}

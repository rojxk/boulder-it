package com.boulderit.service;

import com.boulderit.model.Area;

import java.util.List;

public interface AreaService {
    List<Area> findAll();
    Area findById(String id);
    Area save(Area area);

}

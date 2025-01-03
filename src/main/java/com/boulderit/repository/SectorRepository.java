package com.boulderit.repository;

import com.boulderit.model.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends MongoRepository<Sector, String> {

    @Query("{ 'area_id': ?0 }")
    List<Sector> findByAreaId(String areaId);

}

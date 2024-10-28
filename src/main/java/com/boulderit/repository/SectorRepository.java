package com.boulderit.repository;

import com.boulderit.model.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends MongoRepository<Sector, String> {

}

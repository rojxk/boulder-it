package com.boulderit.repository;

import com.boulderit.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends MongoRepository<Area, String> {

}

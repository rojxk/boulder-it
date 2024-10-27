package com.boulderit.repository;


import com.boulderit.model.ParkingSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ParkingSpotRepository extends MongoRepository<ParkingSpot, String> {

}

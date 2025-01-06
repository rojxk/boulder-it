package com.boulderit.repository;


import com.boulderit.model.ParkingSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ParkingSpotRepository extends MongoRepository<ParkingSpot, String> {

    @Query("{ '_id': ?0 }")
    ParkingSpot findByAreaId(String areaId);

    @Query(value = "{ '_id': ?0 }", delete = true)
    void deleteByParkingId(String parkingId);

}

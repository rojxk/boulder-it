package com.boulderit.repository;

import com.boulderit.model.Area;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AreaRepository extends MongoRepository<Area, String> {

    List<Area> findByCoordinatesNear(GeoJsonPoint location, Distance distance);

    @Query("{'coordinates': {$geoWithin: {$centerSphere: [[?0, ?1], ?2]}}}")
    List<Area> findAreasWithinRadius(double longitude, double latitude, double radius);

    // nearest areas sorted by distance
    @Query("{'coordinates': {$near: {$geometry: {type: 'Point', coordinates: [?0, ?1]}, $maxDistance: ?2}}}")
    List<Area> findNearestAreas(double longitude, double latitude, double maxDistance);


}

package com.boulderit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document("areas")
public class Area {

    @Id
    private String id;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coordinates;
    @Field("created_at")
    private Date createdAt;
    private String description;
    private List<String> hazards;

    @DocumentReference(lookup = "{ '_id' : ?#{#self} }")
    @Field("parking_id")
    private ParkingSpot parkingSpot;

    @Field("updated_at")
    private Date updatedAt;

    @Field("best_seasons")
    private List<String> bestSeasons;
    private String name;

    public Area(){

    }

    public Area(String id, GeoJsonPoint coordinates, Date createdAt, String description, List<String> hazards,ParkingSpot parkingSpot, Date updatedAt, List<String> bestSeasons, String name) {
        this.id = id;
        this.coordinates = coordinates;
        this.createdAt = createdAt;
        this.description = description;
        this.hazards = hazards;
        this.parkingSpot = parkingSpot;
        this.updatedAt = updatedAt;
        this.bestSeasons = bestSeasons;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeoJsonPoint getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GeoJsonPoint coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHazards() {
        return hazards;
    }

    public void setHazards(List<String> hazards) {
        this.hazards = hazards;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getBestSeasons() {
        return bestSeasons;
    }

    public void setBestSeasons(List<String> bestSeasons) {
        this.bestSeasons = bestSeasons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id='" + id + '\'' +
                ", coordinates=" + coordinates +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", hazards=" + hazards +
                ", parkingSpot=" + parkingSpot +
                ", updatedAt=" + updatedAt +
                ", bestSeasons=" + bestSeasons +
                ", name='" + name + '\'' +
                '}';
    }
}

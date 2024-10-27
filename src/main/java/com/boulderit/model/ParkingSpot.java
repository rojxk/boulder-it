package com.boulderit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("parking_spot")
public class ParkingSpot {
    @Id
    private String id;
    private Integer capacity;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coordinates;
    private Date created_at = new Date();
    private String description;
    private String name;
    private Date updated_at = new Date();

    public ParkingSpot() {}

    public ParkingSpot(String id, Integer capacity, GeoJsonPoint coordinates, Date created_at, String description, String name, Date updated_at) {
        this.id = id;
        this.capacity = capacity;
        this.coordinates = coordinates;
        this.created_at = created_at;
        this.description = description;
        this.name = name;
        this.updated_at = updated_at;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public GeoJsonPoint getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GeoJsonPoint coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id='" + id + '\'' +
                ", capacity=" + capacity +
                ", coordinates=" + coordinates +
                ", created_at=" + created_at +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", updated_at=" + updated_at +
                '}';
    }
}

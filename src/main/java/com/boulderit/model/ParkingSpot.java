package com.boulderit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("parking_spots")
public class ParkingSpot {
    @Id
    private String id;
    private Integer capacity;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coordinates;
    @Field("created_at")
    private Date createdAt = new Date();
    private String description;
    private String name;
    @Field("updated_at")
    private Date updatedAt = new Date();
    private String type;

    public ParkingSpot() {}

    public ParkingSpot(String id, Integer capacity, GeoJsonPoint coordinates, Date createdAt, String description, String name, Date updatedAt, String type) {
        this.id = id;
        this.capacity = capacity;
        this.coordinates = coordinates;
        this.createdAt= createdAt;
        this.description = description;
        this.name = name;
        this.updatedAt = updatedAt;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id='" + id + '\'' +
                ", capacity=" + capacity +
                ", coordinates=" + coordinates +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

package com.boulderit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("sectors")
public class Sector {
    @Id
    private String id;
    @DocumentReference
    @Field("area_id")
    private Area area;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coordinates;
    @Field("created_at")
    private Date createdAt;
    private String name;
    @Field("updated_at")
    private Date updatedAt;

    public Sector(){

    }

    public Sector(String id, Area area, GeoJsonPoint coordinates, Date createdAt, String name, Date updatedAt) {
        this.id = id;
        this.area = area;
        this.coordinates = coordinates;
        this.createdAt = createdAt;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    @Override
    public String toString() {
        return "Sector{" +
                "id='" + id + '\'' +
                ", area=" + area +
                ", coordinates=" + coordinates +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

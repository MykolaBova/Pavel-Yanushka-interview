package org.pavel.yanushka.server.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "places")
public class PlacesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String placeId;
    @Column
    private String name;
    @Column
    private Double lat;
    @Column
    private Double lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }


    public static final class PlacesEntityBuilder {
        private Long id;
        private String placeId;
        private String name;
        private Double lat;
        private Double lng;

        private PlacesEntityBuilder() {
        }

        public static PlacesEntityBuilder aPlacesEntity() {
            return new PlacesEntityBuilder();
        }

        public PlacesEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PlacesEntityBuilder placeId(String placeId) {
            this.placeId = placeId;
            return this;
        }

        public PlacesEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlacesEntityBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public PlacesEntityBuilder lng(Double lng) {
            this.lng = lng;
            return this;
        }

        public PlacesEntity build() {
            PlacesEntity placesEntity = new PlacesEntity();
            placesEntity.setId(id);
            placesEntity.setPlaceId(placeId);
            placesEntity.setName(name);
            placesEntity.setLat(lat);
            placesEntity.setLng(lng);
            return placesEntity;
        }
    }
}

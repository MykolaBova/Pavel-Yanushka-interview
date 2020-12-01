package org.pavel.yanushka.common.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Candidate implements Serializable {
    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    private String name;

    private float rating;

    private List<Photos> photos;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    public static final class CandidatesBuilder {
        private String placeId;
        private String formattedAddress;
        private String name;
        private float rating;
        private List<Photos> photos;

        private CandidatesBuilder() {
        }

        public static CandidatesBuilder aCandidates() {
            return new CandidatesBuilder();
        }

        public CandidatesBuilder placeId(String placeId) {
            this.placeId = placeId;
            return this;
        }

        public CandidatesBuilder formattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
            return this;
        }

        public CandidatesBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CandidatesBuilder rating(float rating) {
            this.rating = rating;
            return this;
        }

        public CandidatesBuilder photos(List<Photos> photos) {
            this.photos = photos;
            return this;
        }

        public Candidate build() {
            Candidate candidate = new Candidate();
            candidate.setPlaceId(placeId);
            candidate.setFormattedAddress(formattedAddress);
            candidate.setName(name);
            candidate.setRating(rating);
            candidate.setPhotos(photos);
            return candidate;
        }
    }
}

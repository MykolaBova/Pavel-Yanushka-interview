package org.pavel.yanushka.common.model;

import java.io.Serializable;
import java.util.List;

public class Place implements Serializable {
    private List<Candidate> candidates;
    private String status;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static final class PlaceBuilder {
        private List<Candidate> candidates;
        private String status;

        private PlaceBuilder() {
        }

        public static PlaceBuilder aPlace() {
            return new PlaceBuilder();
        }

        public PlaceBuilder candidates(List<Candidate> candidates) {
            this.candidates = candidates;
            return this;
        }

        public PlaceBuilder status(String status) {
            this.status = status;
            return this;
        }

        public Place build() {
            Place place = new Place();
            place.setCandidates(candidates);
            place.setStatus(status);
            return place;
        }
    }
}

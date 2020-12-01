package org.pavel.yanushka.common.models;

import java.io.Serializable;
import java.util.List;

public class Place implements Serializable {
    private List<Candidate> candidates;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public static final class PlaceBuilder {
        private List<Candidate> candidates;

        private PlaceBuilder() {
        }

        public static PlaceBuilder aPlace() {
            return new PlaceBuilder();
        }

        public PlaceBuilder candidates(List<Candidate> candidates) {
            this.candidates = candidates;
            return this;
        }

        public Place build() {
            Place place = new Place();
            place.setCandidates(candidates);
            return place;
        }
    }
}

package org.pavel.yanushka.common.models;

import java.io.Serializable;

public class City implements Serializable {
    private String placeId;
    private String name;

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

    public static final class Builder {
        private String placeId;
        private String name;

        private Builder() {
        }

        public static Builder aSuggest() {
            return new Builder();
        }

        public Builder placeId(String placeId) {
            this.placeId = placeId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public City build() {
            City city = new City();
            city.setPlaceId(placeId);
            city.setName(name);
            return city;
        }
    }
}

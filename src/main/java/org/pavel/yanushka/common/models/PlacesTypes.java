package org.pavel.yanushka.common.models;

public enum PlacesTypes {
    BANK("bank"),
    BAR("bar"),
    BEAUTY_SALON("beauty_salon"),
    BUS_STATION("bus_station"),
    CAFE("cafe"),
    CHURCH("church"),
    CITY_HALL("city_hall"),
    CLOTHING_STORE("clothing_store"),
    DOCTOR("doctor"),
    DRUGSTORE("drugstore"),
    HOSPITAL("hospital"),
    MUSEUM("museum"),
    NIGHT_CLUB("night_club"),
    PARK("park"),
    RESTAURANT("restaurant"),
    STORE("store"),
    ZOO("zoo");

    private final String placeType;

    PlacesTypes(String placeType) {
        this.placeType = placeType;
    }

    public String toUrlValue() {
        return this.placeType;
    }

    @Override
    public String toString() {
        return this.placeType;
    }

}

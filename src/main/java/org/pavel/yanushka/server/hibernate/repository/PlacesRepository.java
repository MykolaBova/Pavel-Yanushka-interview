package org.pavel.yanushka.server.hibernate.repository;

import org.pavel.yanushka.server.hibernate.entities.PlacesEntity;

import java.util.List;

public interface PlacesRepository {
    List<PlacesEntity> getAllPlaces();

    PlacesEntity getPlaceByName(String name);
}

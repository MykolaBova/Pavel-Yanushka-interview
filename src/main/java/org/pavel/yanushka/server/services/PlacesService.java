package org.pavel.yanushka.server.services;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import org.pavel.yanushka.common.model.Place;
import org.pavel.yanushka.server.hibernate.entities.PlacesEntity;
import org.pavel.yanushka.server.hibernate.repository.PlacesRepository;
import org.pavel.yanushka.server.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Service
public class PlacesService {

    @Value("${application.google.api}")
    private String apiKey;

    private final PlacesRepository placesRepository;

    private GeoApiContext geoApiContext;

    public PlacesService(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public Place getPlacesForCity(String query) {
        PlacesEntity city = placesRepository.getPlaceByName(query);
        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(getGeoApi(),
                new LatLng(city.getLat(), city.getLng()));
        nearbySearchRequest.radius(2000);
        PlacesSearchResponse placesSearchResponse = nearbySearchRequest.awaitIgnoreError();

        return PlaceMapper.candidatesToPlace(placesSearchResponse);
    }

    private GeoApiContext getGeoApi() {
        if (geoApiContext == null) {
            geoApiContext = new GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build();
        }
        return geoApiContext;
    }
}

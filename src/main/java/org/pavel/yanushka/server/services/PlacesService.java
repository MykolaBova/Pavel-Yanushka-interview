package org.pavel.yanushka.server.services;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import org.pavel.yanushka.common.model.Place;
import org.pavel.yanushka.server.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Service
public class PlacesService {

    @Value("${application.google.api}")
    private String apiKey;

    private final GeoApiContext geoApiContext = new GeoApiContext.Builder()
            .apiKey(apiKey)
            .build();

    public Place getPlacesForCity(String query) {
        // TODO change to use values from db
        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext,
                new LatLng(53.6687634, 23.8222673));
        nearbySearchRequest.radius(2000);
        PlacesSearchResponse placesSearchResponse = nearbySearchRequest.awaitIgnoreError();

        return PlaceMapper.candidatesToPlace(placesSearchResponse);
    }
}

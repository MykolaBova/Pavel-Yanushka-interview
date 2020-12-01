package org.pavel.yanushka.server.services;

import com.google.maps.*;
import com.google.maps.model.*;
import org.pavel.yanushka.common.models.Photos;
import org.pavel.yanushka.server.persistence.entities.PlacesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// TODO add handling of errors
@Service
public class GooglePlacesService {

    private static final Logger LOG = LoggerFactory.getLogger(GooglePlacesService.class);
    @Value("${application.google.api}")
    private String apiKey;
    private final GeoApiContext geoApiContext;
    private final PlaceAutocompleteRequest.SessionToken sessionToken;
    private static final String LANGUAGE = "en";
    private static final int RADIUS = 5000;
    private static final int IMAGE_MAX_HEIGHT_PX = 250;

    public GooglePlacesService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
        this.sessionToken = new PlaceAutocompleteRequest.SessionToken();
    }

    public PlacesSearchResponse nearbySearchQuery(PlacesEntity place) {
        NearbySearchRequest request = PlacesApi.nearbySearchQuery(geoApiContext,
                new LatLng(place.getLat(), place.getLng()))
                .language(LANGUAGE)
                .radius(RADIUS);
        return request.awaitIgnoreError();
    }

    public PlaceDetails placeDetails(String placeId) {
        return PlacesApi.placeDetails(geoApiContext, placeId).awaitIgnoreError();
    }

    public ImageResult photo(Photos photo) {
        return PlacesApi.photo(geoApiContext,
                photo.getPhotoReference()).maxHeight(IMAGE_MAX_HEIGHT_PX).awaitIgnoreError();
    }

    public AutocompletePrediction[] placeAutocomplete(String query) {
        return PlacesApi.placeAutocomplete(geoApiContext, query, sessionToken)
                .types(PlaceAutocompleteType.CITIES).awaitIgnoreError();
    }

}

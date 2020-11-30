package org.pavel.yanushka.server.services;

import com.google.maps.*;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.PlaceAutocompleteType;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import org.pavel.yanushka.common.model.CitySuggests;
import org.pavel.yanushka.common.model.Photos;
import org.pavel.yanushka.common.model.Place;
import org.pavel.yanushka.server.mapper.PlaceMapper;
import org.pavel.yanushka.server.persistence.repository.PlacesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Base64;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Service
public class PlacesService {

    @Value("${application.google.api}")
    private String apiKey;
    private static final String LANGUAGE = "en";
    private static final int RADIUS = 5000;
    private static final int IMAGE_MAX_HEIGHT_PX = 250;

    private final PlacesRepository placesRepository;
    private final GeoApiContext geoApiContext;
    private final PlaceAutocompleteRequest.SessionToken sessionToken;

    public PlacesService(PlacesRepository placesRepository, GeoApiContext geoApiContext) {
        this.placesRepository = placesRepository;
        this.geoApiContext = geoApiContext;
        this.sessionToken = new PlaceAutocompleteRequest.SessionToken();
    }

    public Place getPlacesForCity(String placeId) {
        PlaceDetails placeDetails = PlacesApi.placeDetails(geoApiContext, placeId).awaitIgnoreError();
        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext, placeDetails.geometry.location)
                .language(LANGUAGE)
                .radius(RADIUS);
        PlacesSearchResponse placesSearchResponse = nearbySearchRequest.awaitIgnoreError();
        Place place = PlaceMapper.candidatesToPlace(placesSearchResponse);
        downloadImages(place);
        return place;
    }

    private void downloadImages(Place place) {
        place.getCandidates().forEach(candidate -> {
            if (!candidate.getPhotos().isEmpty()) {
                Photos photo = candidate.getPhotos().get(0);
                ImageResult imageResult = PlacesApi.photo(geoApiContext,
                        photo.getPhotoReference()).maxHeight(IMAGE_MAX_HEIGHT_PX).awaitIgnoreError();
                String encoded = Base64.getEncoder().encodeToString(imageResult.imageData);
                photo.setPhoto(encoded);
            }
        });
    }

    public CitySuggests getCitySuggests(String query) {
        AutocompletePrediction[] autocompletePredictions = PlacesApi.placeAutocomplete(geoApiContext, query, sessionToken)
                .types(PlaceAutocompleteType.CITIES).awaitIgnoreError();
        return PlaceMapper.candidatesToCitySuggests(autocompletePredictions);
    }
}

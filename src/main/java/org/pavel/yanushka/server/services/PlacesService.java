package org.pavel.yanushka.server.services;

import com.google.maps.*;
import com.google.maps.model.*;
import org.pavel.yanushka.common.model.CitySuggests;
import org.pavel.yanushka.common.model.Photos;
import org.pavel.yanushka.common.model.Place;
import org.pavel.yanushka.server.mapper.PlaceMapper;
import org.pavel.yanushka.server.persistence.entities.PlacesEntity;
import org.pavel.yanushka.server.persistence.repository.PlacesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

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
        PlacesEntity place = getPlace(placeId);
        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext,
                new LatLng(place.getLat(), place.getLng()))
                .language(LANGUAGE)
                .radius(RADIUS);
        PlacesSearchResponse placesSearchResponse = nearbySearchRequest.awaitIgnoreError();
        Place result = PlaceMapper.candidatesToPlace(placesSearchResponse);
        downloadImages(result);
        return result;
    }

    private PlacesEntity getPlace(String placeId) {
        PlacesEntity place = placesRepository.findByPlaceId(placeId);
        if (Objects.isNull(place)) {
            PlaceDetails placeDetails = PlacesApi.placeDetails(geoApiContext, placeId).awaitIgnoreError();
            place = placesRepository.save(PlacesEntity.PlacesEntityBuilder.aPlacesEntity()
                    .placeId(placeDetails.placeId)
                    .name(placeDetails.name)
                    .lat(placeDetails.geometry.location.lat)
                    .lng(placeDetails.geometry.location.lng)
                    .build());
        }
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

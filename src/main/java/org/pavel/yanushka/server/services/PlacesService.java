package org.pavel.yanushka.server.services;

import com.google.maps.ImageResult;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import org.pavel.yanushka.common.models.CitySuggests;
import org.pavel.yanushka.common.models.Photos;
import org.pavel.yanushka.common.models.Place;
import org.pavel.yanushka.server.mapper.PlaceMapper;
import org.pavel.yanushka.server.persistence.entities.PlacesEntity;
import org.pavel.yanushka.server.persistence.repository.PlacesRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@Service
public class PlacesService {
    private final PlacesRepository placesRepository;
    private final GooglePlacesService googlePlacesService;

    public PlacesService(PlacesRepository placesRepository, GooglePlacesService googlePlacesService) {
        this.placesRepository = placesRepository;
        this.googlePlacesService = googlePlacesService;
    }

    public Place getPlacesForCity(String placeId) {
        PlacesEntity place = getPlace(placeId);
        PlacesSearchResponse placesSearchResponse = googlePlacesService.nearbySearchQuery(place);
        Place result = PlaceMapper.candidatesToPlace(place.getName(), placesSearchResponse);
        downloadImages(result);
        return result;
    }

    private PlacesEntity getPlace(String placeId) {
        PlacesEntity place = placesRepository.findByPlaceId(placeId);
        if (Objects.isNull(place)) {
            PlaceDetails placeDetails = googlePlacesService.placeDetails(placeId);
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
                ImageResult imageResult = googlePlacesService.photo(photo);
                String encoded = Base64.getEncoder().encodeToString(imageResult.imageData);
                photo.setPhoto(encoded);
            }
        });
    }

    public CitySuggests getCitySuggests(String query) {
        AutocompletePrediction[] autocompletePredictions = googlePlacesService.placeAutocomplete(query);
        return PlaceMapper.candidatesToCitySuggests(autocompletePredictions);
    }
}

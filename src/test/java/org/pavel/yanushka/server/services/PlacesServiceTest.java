package org.pavel.yanushka.server.services;

import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.AutocompleteStructuredFormatting;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pavel.yanushka.common.models.Candidate;
import org.pavel.yanushka.common.models.City;
import org.pavel.yanushka.common.models.CitySuggests;
import org.pavel.yanushka.common.models.Place;
import org.pavel.yanushka.server.persistence.entities.PlacesEntity;
import org.pavel.yanushka.server.persistence.repository.PlacesRepository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlacesServiceTest {
    private final Fixture fixture = new Fixture();
    @Mock
    private GooglePlacesService googlePlacesService;
    @Mock
    private PlacesRepository placesRepository;
    @InjectMocks
    private PlacesService placesService;


    @Test
    void shouldReturnSuggests() {
        when(googlePlacesService.placeAutocomplete(eq("mainText"))).thenReturn(fixture.predictions());
        CitySuggests suggest = placesService.getCitySuggests("mainText");

        Assert.assertNotNull(suggest.getSuggestsList());
        Assert.assertEquals(1, suggest.getSuggestsList().size());
        City city = suggest.getSuggestsList().get(0);
        Assert.assertEquals("mainText", city.getName());
        Assert.assertEquals("placeId", city.getPlaceId());
    }

    @Test
    void shouldGetPlacesForCity() throws Exception {
        PlacesEntity placesEntity = fixture.placesEntity();
        when(placesRepository.findByPlaceId(eq("placeId"))).thenReturn(placesEntity);
        when(googlePlacesService.nearbySearchQuery(eq(placesEntity))).thenReturn(fixture.placesSearchResponse());
        Place place = placesService.getPlacesForCity("placeId");

        Assert.assertNotNull(place.getCandidates());
        Assert.assertEquals(1, place.getCandidates().size());
        Candidate candidate = place.getCandidates().get(0);
        Assert.assertEquals("place", candidate.getName());
        Assert.assertEquals("placeId", candidate.getPlaceId());
    }

    static class Fixture {
        AutocompletePrediction[] predictions() {
            AutocompletePrediction[] predictions = new AutocompletePrediction[1];
            AutocompletePrediction prediction = new AutocompletePrediction();
            prediction.placeId = "placeId";
            prediction.structuredFormatting = new AutocompleteStructuredFormatting();
            prediction.structuredFormatting.mainText = "mainText";
            predictions[0] = prediction;
            return predictions;
        }

        PlacesEntity placesEntity() {
            return PlacesEntity.PlacesEntityBuilder.aPlacesEntity()
                    .name("grodno")
                    .lng(1d)
                    .lat(1d)
                    .build();
        }

        PlacesSearchResponse placesSearchResponse() {
            PlacesSearchResponse placesSearchResponse = new PlacesSearchResponse();
            placesSearchResponse.results = new PlacesSearchResult[1];
            PlacesSearchResult placesSearchResult = new PlacesSearchResult();
            placesSearchResult.name = "place";
            placesSearchResult.placeId = "placeId";
            placesSearchResponse.results[0] = placesSearchResult;
            return placesSearchResponse;
        }
    }
}

package org.pavel.yanushka.server.mapper;

import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.pavel.yanushka.common.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class PlaceMapper {

    private PlaceMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Place candidatesToPlace(String placeName, PlacesSearchResponse placesSearchResponse) {
        return Place.PlaceBuilder.aPlace()
                .candidates(buildCandidates(placeName, placesSearchResponse.results))
                .build();
    }

    public static CitySuggests candidatesToCitySuggests(AutocompletePrediction[] predictions) {
        return CitySuggests.CitySuggestsBuilder.aCitySuggests()
                .suggestsList(
                        Arrays.stream(predictions)
                                .map(p -> City.Builder.aSuggest()
                                        .name(p.structuredFormatting.mainText)
                                        .placeId(p.placeId)
                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }

    private static List<Candidate> buildCandidates(String placeName, PlacesSearchResult[] results) {
        List<Candidate> candidates = new ArrayList<>();
        if (results != null) {
            candidates.addAll(Arrays.stream(results)
                    .filter(result -> !result.name.equals(placeName))
                    .map(result -> Candidate.CandidatesBuilder.aCandidates()
                            .name(result.name)
                            .formattedAddress(result.formattedAddress)
                            .placeId(result.placeId)
                            .photos(buildPhotos(result.photos))
                            .rating(result.rating)
                            .build()).collect(Collectors.toList()));
        }
        return candidates;
    }

    private static List<Photos> buildPhotos(Photo[] photos) {
        List<Photos> photosList = new ArrayList<>();
        if (photos != null) {
            return Arrays.stream(photos)
                    .map(photo -> Photos.PhotosBuilder.aPhotos()
                            .height(photo.height)
                            .width(photo.width)
                            .photoReference(photo.photoReference)
                            .htmlAttributions(Arrays.asList(photo.htmlAttributions))
                            .build()).collect(Collectors.toList());
        }
        return photosList;
    }
}

package org.pavel.yanushka.server.mapper;

import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import org.pavel.yanushka.common.model.Candidate;
import org.pavel.yanushka.common.model.Photos;
import org.pavel.yanushka.common.model.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceMapper {

    private PlaceMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Place candidatesToPlace(PlacesSearchResponse placesSearchResponse) {
        List<Candidate> candidates = new ArrayList<>();
        Arrays.stream(placesSearchResponse.results).forEach(result ->
                candidates.add(Candidate.CandidatesBuilder.aCandidates()
                        .name(result.name)
                        .formattedAddress(result.formattedAddress)
                        .placeId(result.placeId)
                        .photos(buildPhotos(result.photos))
                        .rating(result.rating)
                        .build()));

        return Place.PlaceBuilder.aPlace()
                .candidates(candidates)
                .status("OK")
                .build();
    }

    private static List<Photos> buildPhotos(Photo[] photos) {
        List<Photos> photosList = new ArrayList<>();
        if (photos != null) {
            Arrays.stream(photos).forEach(photo -> photosList.add(Photos.PhotosBuilder.aPhotos()
                    .height(photo.height)
                    .width(photo.width)
                    .photoReference(photo.photoReference)
                    .htmlAttributions(Arrays.asList(photo.htmlAttributions))
                    .build()));
        }
        return photosList;
    }
}

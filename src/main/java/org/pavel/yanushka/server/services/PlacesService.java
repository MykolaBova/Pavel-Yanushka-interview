package org.pavel.yanushka.server.services;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import org.pavel.yanushka.common.model.Candidate;
import org.pavel.yanushka.common.model.Photos;
import org.pavel.yanushka.common.model.Place;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class PlacesService {
    // todo change to use app props
    private static final String API = "AIzaSyBG70wesADepweBeIwTpkGaSdY4putdxQs";

//    public Place getSuggestForPlaces(String query) throws IOException, ApiRequestException {
//        GeoApiContext geoApiContext = new GeoApiContext.Builder()
//                .apiKey(API)
//                .build();
//        PlaceDetails placeDetails;
//        try {
//            FindPlaceFromText findPlaceFromText = PlacesApi.findPlaceFromText(geoApiContext, query,
//                    FindPlaceFromTextRequest.InputType.TEXT_QUERY).await();
//            placeDetails = PlacesApi.placeDetails(geoApiContext, findPlaceFromText.candidates[0].placeId).await();
//        } catch (ApiException | InterruptedException e) {
//            e.printStackTrace();
//            throw new ApiRequestException("");
//        }
//
//        return buildPlaceModel(placeDetails);
//    }

    public Place getForPlaces(String query) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(API)
                .build();
        List<Candidate> candidates = new ArrayList<>();
        // TODO change to use values from db
        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext,
                new LatLng(53.6687634, 23.8222673));
        nearbySearchRequest.radius(2000);
        PlacesSearchResponse placesSearchResponse = nearbySearchRequest.awaitIgnoreError();
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

    private List<Photos> buildPhotos(Photo[] photos) {
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

//    private Place buildPlaceModel(PlaceDetails placeDetails) {
//        List<Photos> photos = new ArrayList<>();
//        List<Candidate> candidates = new ArrayList<>();
//        Arrays.stream(placeDetails.photos).forEach(photo -> photos.add(Photos.PhotosBuilder.aPhotos()
//                .height(photo.height)
//                .width(photo.width)
//                .htmlAttributions(Arrays.asList(photo.htmlAttributions))
//                .photoReference(photo.photoReference)
//                .build()));
//        candidates.add(Candidate.CandidatesBuilder.aCandidates()
//                .name(placeDetails.name)
//                .formattedAddress(placeDetails.formattedAddress)
//                .photos(photos)
//                .placeId(placeDetails.placeId)
//                .rating(placeDetails.rating)
//                .build());
//        return Place.PlaceBuilder.aPlace()
//                .candidates(candidates)
//                .status("OK")
//                .build();
//    }
}

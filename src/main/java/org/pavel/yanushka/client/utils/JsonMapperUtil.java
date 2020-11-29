package org.pavel.yanushka.client.utils;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.google.gwt.core.client.GWT;
import org.pavel.yanushka.common.model.Place;

public class JsonMapperUtil {
    public interface PlaceMapper extends ObjectMapper<Place> {
    }

    private static final PlaceMapper PLACE_MAPPER = GWT.create(PlaceMapper.class);
    private static final JsonDeserializationContext CONTEXT = JsonDeserializationContext.builder()
            .failOnUnknownProperties(false)
            .build();

    private JsonMapperUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Place getPlaceModel(String response) {
        Place place = null;
        try {
            place = PLACE_MAPPER.read(response, CONTEXT);
        } catch (JsonDeserializationException e) {
            GWT.log(e.getMessage());
        }
        return place;
    }
}

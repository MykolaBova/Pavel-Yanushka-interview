package org.pavel.yanushka.client.utils;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.google.gwt.core.client.GWT;
import org.pavel.yanushka.common.model.CitySuggests;
import org.pavel.yanushka.common.model.Place;

import java.io.Serializable;

public final class JsonMapperUtil {
    public interface PlaceMapper extends ObjectMapper<Place> {
    }

    public interface SuggestsMapper extends ObjectMapper<CitySuggests> {
    }

    private static final PlaceMapper PLACE_MAPPER = GWT.create(PlaceMapper.class);
    private static final SuggestsMapper SUGGESTS_MAPPER = GWT.create(SuggestsMapper.class);
    private static final JsonDeserializationContext CONTEXT = JsonDeserializationContext.builder()
            .failOnUnknownProperties(false)
            .build();

    private JsonMapperUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Place getPlaceModel(String response) {
        return (Place) getModel(PLACE_MAPPER, response);
    }

    public static CitySuggests getSuggestsModel(String response) {
        return (CitySuggests) getModel(SUGGESTS_MAPPER, response);
    }

    private static Object getModel(ObjectMapper<? extends Serializable> mapper, String response) {
        Serializable suggests = null;
        try {
            suggests = mapper.read(response, CONTEXT);
        } catch (JsonDeserializationException e) {
            GWT.log(e.getMessage());
        }
        return suggests;
    }
}

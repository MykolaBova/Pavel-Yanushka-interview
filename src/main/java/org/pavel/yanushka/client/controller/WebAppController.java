package org.pavel.yanushka.client.controller;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import org.pavel.yanushka.client.event.GetPlacesEvent;
import org.pavel.yanushka.client.model.ModelHandler;
import org.pavel.yanushka.client.ui.MainPanel;
import org.pavel.yanushka.common.model.Place;

public class WebAppController {
    private static final String REST_PREFIX = "rest/";
    private final SimpleEventBus eventBus;
    private final ModelHandler modelHandler;
    private final MainPanel mainPanel;

    public interface PlaceMapper extends ObjectMapper<Place> {
    }

    @Inject
    public WebAppController(SimpleEventBus eventBus, ModelHandler modelHandler, MainPanel mainPanel) {
        this.eventBus = eventBus;
        this.modelHandler = modelHandler;
        this.mainPanel = mainPanel;
    }

    public void bindHandlers() {
        eventBus.addHandler(GetPlacesEvent.TYPE, event -> getPlacesList(event.getCityTitle()));
    }

    protected void showPlacesList(Place list) {
        modelHandler.reloadAll(list);
        mainPanel.showPlaces();
    }

    protected void getPlacesList(String cityName) {
        String pageBaseUrl = GWT.getHostPageBaseURL();
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, pageBaseUrl + REST_PREFIX + "places/get/"
                + cityName);
        rb.setCallback(new RequestCallback() {

            public void onError(Request request, Throwable e) {
                Window.alert("error = " + e.getMessage());
            }

            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    String text = response.getText();
                    Place place = null;
                    PlaceMapper mapper = GWT.create(PlaceMapper.class);
                    JsonDeserializationContext context = JsonDeserializationContext.builder()
                            .failOnUnknownProperties(false)
                            .build();
                    try {
                        place = mapper.read(text, context);
                    } catch (JsonDeserializationException e) {
                        GWT.log(e.getMessage());
                    }
                    showPlacesList(place);
                }
            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            e.printStackTrace();
            Window.alert("error = " + e.getMessage());
        }
    }
}

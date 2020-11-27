package org.pavel.yanushka.client.controller;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Inject;
import org.pavel.yanushka.client.event.GetPlacesEvent;
import org.pavel.yanushka.client.model.ModelHandler;
import org.pavel.yanushka.client.rest.RequestBuilderService;
import org.pavel.yanushka.client.ui.MainPanel;
import org.pavel.yanushka.client.utils.JsonMapperUtil;
import org.pavel.yanushka.common.model.Place;

public class WebAppController {
    private final SimpleEventBus eventBus;
    private final ModelHandler modelHandler;
    private final MainPanel mainPanel;
    private final RequestBuilderService requestBuilderService;

    @Inject
    public WebAppController(SimpleEventBus eventBus, ModelHandler modelHandler, MainPanel mainPanel,
                            RequestBuilderService requestBuilderService) {
        this.eventBus = eventBus;
        this.modelHandler = modelHandler;
        this.mainPanel = mainPanel;
        this.requestBuilderService = requestBuilderService;
    }

    public void bindHandlers() {
        eventBus.addHandler(GetPlacesEvent.TYPE, event -> getPlacesList(event.getCityTitle()));
    }

    protected void showPlacesList(Place list) {
        modelHandler.reloadAll(list);
        mainPanel.showPlaces();
    }

    protected void getPlacesList(String cityName) {
        requestBuilderService.makeGetPlacesRequest(cityName, response ->
                showPlacesList(JsonMapperUtil.getPlaceModel(response.getText())));
    }
}

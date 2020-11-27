package org.pavel.yanushka.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.pavel.yanushka.client.event.GetPlacesEvent;
import org.pavel.yanushka.client.model.ModelHandler;
import org.pavel.yanushka.client.ui.component.ImageButton;
import org.pavel.yanushka.common.model.Candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPanel extends Composite {

    private static final MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);

    interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
    }

    @UiField
    ImageButton getPlacesButton;

    @UiField
    ListBox cityBox;

    @UiField
    FlowPanel citiesPanel;

    Map<Candidate, PlacesWidget> placeWidgets;

    private final SimpleEventBus eventBus;

    private final ModelHandler modelHandler;

    @Inject
    public MainPanel(SimpleEventBus eventBus, ModelHandler modelHandler) {
        this.eventBus = eventBus;
        initWidget(uiBinder.createAndBindUi(this));
        placeWidgets = new HashMap<>();
        this.modelHandler = modelHandler;
        // todo get this from db
        cityBox.addItem("Grodno");
        cityBox.addItem("Minsk");
        cityBox.setSelectedIndex(0);
    }

    @UiHandler("getPlacesButton")
    void onGetPlacesButtonClick(ClickEvent e) {
        String cityText = cityBox.getSelectedItemText();
        eventBus.fireEvent(new GetPlacesEvent(cityText));
    }

    public void addPlaceToPanel(Candidate candidate) {
        PlacesWidget placeWidget = new PlacesWidget(candidate, eventBus);
        citiesPanel.add(placeWidget);
        placeWidgets.putIfAbsent(candidate, placeWidget);
    }

    public void removeAllPlaces() {
        citiesPanel.clear();
        placeWidgets.clear();
    }

    public void showPlaces() {
        removeAllPlaces();
        List<Candidate> all = modelHandler.getAll();
        if (!all.isEmpty()) {
            all.forEach(this::addPlaceToPanel);
        }
    }
}

package org.pavel.yanushka.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.pavel.yanushka.common.models.Candidate;

public class PlacesWidget extends Composite {

    private static final PlacesWidgetUiBinder uiBinder = GWT.create(PlacesWidgetUiBinder.class);

    interface PlacesWidgetUiBinder extends UiBinder<Widget, PlacesWidget> {
    }

    @UiField
    InlineLabel name;

    @UiField
    HTMLPanel placeImage;

    private Candidate currentCandidate;
    private SimpleEventBus eventBus;

    public PlacesWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public PlacesWidget(Candidate candidate, SimpleEventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(uiBinder.createAndBindUi(this));
        this.currentCandidate = candidate;
        name.setText("Name: " + candidate.getName()
                + " Rating: " + candidate.getRating());

        HorizontalPanel imagePanel = new HorizontalPanel();
        HTML html = new HTML("<img src=\"data:image/png;base64, "
                + candidate.getPhotos().get(0).getPhoto()
                + " \" />");
        imagePanel.add(html);

        placeImage.add(imagePanel);
    }

}

package org.pavel.yanushka.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GetPlacesEvent extends GwtEvent<GetPlacesEventHandler> {

    public static final Type<GetPlacesEventHandler> TYPE = new Type<>();

    private final String cityTitle;

    public String getCityTitle() {
        return cityTitle;
    }

    public GetPlacesEvent(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    @Override
    protected void dispatch(GetPlacesEventHandler handler) {
        handler.onGetPlacesEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<GetPlacesEventHandler> getAssociatedType() {
        return TYPE;
    }

}

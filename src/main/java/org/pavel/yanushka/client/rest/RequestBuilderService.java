package org.pavel.yanushka.client.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;

import java.util.function.Consumer;

public class RequestBuilderService {
    private static final String REST_PREFIX = "rest/";

    public void makeGetPlacesRequest(String cityName, Consumer<Response> consumer) {
        getRequest(getPlacesUrl(cityName), consumer);
    }

    private void getRequest(String url, Consumer<Response> consumer) {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable e) {
                Window.alert("Server Error = " + e.getMessage());
            }

            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    consumer.accept(response);
                }
            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            GWT.log(e.getMessage());
            Window.alert("Server Error = " + e.getMessage());
        }
    }

    private String getPlacesUrl(String cityName) {
        return GWT.getHostPageBaseURL() + REST_PREFIX + "places/get/" + cityName;
    }
}

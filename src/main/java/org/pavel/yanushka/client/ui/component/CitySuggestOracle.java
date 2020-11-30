package org.pavel.yanushka.client.ui.component;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;
import com.google.gwt.user.client.ui.SuggestOracle;
import org.pavel.yanushka.client.rest.RequestBuilderService;
import org.pavel.yanushka.client.utils.JsonMapperUtil;
import org.pavel.yanushka.common.model.CitySuggests;

import java.util.stream.Collectors;

public class CitySuggestOracle extends SuggestOracle {

    private final RequestBuilderService requestBuilderService;

    private static final int REQUEST_CITIES_DELAY_MS = 500;
    private CitySuggests latestSuggestions = new CitySuggests();

    private Request request;
    private Callback callback;

    public CitySuggestOracle(RequestBuilderService requestBuilderService) {
        this.requestBuilderService = requestBuilderService;
    }

    private final Timer timer = new Timer() {
        public void run() {
            requestBuilderService.makeGetSuggestsRequest(request.getQuery(), response -> {
                CitySuggests citySuggests = JsonMapperUtil.getSuggestsModel(response.getText());
                if (citySuggests == null || citySuggests.getSuggestsList().isEmpty()) {
                    Window.alert("City with name - " + request.getQuery() + " not found.");
                } else {
                    latestSuggestions = citySuggests;
                    Response suggestResponse = new Response(citySuggests.getSuggestsList().stream()
                            .map(s -> new MultiWordSuggestion(s.getName(), s.getName()))
                            .collect(Collectors.toList()));
                    callback.onSuggestionsReady(request, suggestResponse);
                }
            });
        }
    };

    @Override
    public void requestSuggestions(Request request, Callback callback) {
        resetTimerCountdown(request, callback);
    }

    public CitySuggests getLatestSuggestions() {
        return latestSuggestions;
    }

    private void resetTimerCountdown(Request request, Callback callback) {
        this.request = request;
        this.callback = callback;
        timer.cancel();
        timer.schedule(REQUEST_CITIES_DELAY_MS);
    }
}

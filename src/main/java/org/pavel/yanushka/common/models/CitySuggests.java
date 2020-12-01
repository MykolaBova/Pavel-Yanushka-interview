package org.pavel.yanushka.common.models;

import java.io.Serializable;
import java.util.List;

public class CitySuggests implements Serializable {
    private List<City> suggestsList;

    public List<City> getSuggestsList() {
        return suggestsList;
    }

    public void setSuggestsList(List<City> suggestsList) {
        this.suggestsList = suggestsList;
    }

    public static final class CitySuggestsBuilder {
        private List<City> suggestsList;

        private CitySuggestsBuilder() {
        }

        public static CitySuggestsBuilder aCitySuggests() {
            return new CitySuggestsBuilder();
        }

        public CitySuggestsBuilder suggestsList(List<City> suggestsList) {
            this.suggestsList = suggestsList;
            return this;
        }

        public CitySuggests build() {
            CitySuggests citySuggests = new CitySuggests();
            citySuggests.setSuggestsList(suggestsList);
            return citySuggests;
        }
    }
}

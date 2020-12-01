package org.pavel.yanushka.client.model;

import org.pavel.yanushka.common.models.Candidate;
import org.pavel.yanushka.common.models.Place;

import java.util.ArrayList;
import java.util.List;

public class ModelHandler {

    List<Candidate> places;

    public ModelHandler() {
        places = new ArrayList<>();
    }

    public void add(Candidate t) {
        places.add(t);
    }

    public void remove(Place t) {
        places.remove(t);
    }

    public void removeAll() {
        places.clear();
    }

    public List<Candidate> getAll() {
        return places;
    }

    public void reloadAll(Place list) {
        places.clear();
        places.addAll(list.getCandidates());
    }

}

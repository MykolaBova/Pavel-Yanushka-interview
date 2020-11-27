package org.pavel.yanushka.server.controllers;

import org.pavel.yanushka.common.model.Place;
import org.pavel.yanushka.server.services.PlacesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class PlacesController {

    private static final Logger logger = LoggerFactory.getLogger(PlacesController.class);

    private final PlacesService placesService;

    public PlacesController(PlacesService placesService) {
        this.placesService = placesService;
    }

//    @GetMapping(value = "/places/{name}")
//    @ResponseStatus(HttpStatus.OK)
//    public Place places(@PathVariable String name) throws IOException, ApiRequestException {
//        return placesService.getSuggestForPlaces(name);
//    }

    @GetMapping(value = "/places/get/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Place getPlaces(@PathVariable String name) {
        return placesService.getForPlaces(name);
    }
}

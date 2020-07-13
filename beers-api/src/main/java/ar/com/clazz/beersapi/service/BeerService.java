package ar.com.clazz.beersapi.service;

import java.util.List;

import ar.com.clazz.beersapi.model.Beer;

public interface BeerService {

    Beer validateAndGetBeer(String id);

    List<Beer> getBeers();

    Beer saveBeer(Beer beer);

    void deleteBeer(Beer beer);

}
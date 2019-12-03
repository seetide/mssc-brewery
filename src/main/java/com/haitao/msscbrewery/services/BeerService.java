package com.haitao.msscbrewery.services;

import com.haitao.msscbrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto saveNewBeer();

    void updateBeer(UUID beerId, BeerDto beerDto);

    BeerDto saveNewBeer(BeerDto any);
}

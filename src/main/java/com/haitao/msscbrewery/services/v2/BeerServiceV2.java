package com.haitao.msscbrewery.services.v2;

import com. haitao.msscbrewery.web.model.v2.BeerDtoV2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BeerServiceV2 {
    BeerDtoV2 getBeerById(UUID beerId);

    BeerDtoV2 saveNewBeer(BeerDtoV2 beerDto);

    void updateBeer(UUID beerId, BeerDtoV2 beerDto);

    void deleteById(UUID beerId);
}

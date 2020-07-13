package ar.com.clazz.beersapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.clazz.beersapi.exception.BeerNotFoundException;
import ar.com.clazz.beersapi.model.Beer;
import ar.com.clazz.beersapi.repository.BeerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    @Override
    public Beer validateAndGetBeer(String id) {
        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(String.format("Beer with id '%s' not found", id)));
    }

    @Override
    public List<Beer> getBeers() {
        return beerRepository.findAll();
    }

    @Override
    public Beer saveBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public void deleteBeer(Beer beer) {
    	beerRepository.delete(beer);
    }

}
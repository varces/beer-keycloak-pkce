package ar.com.clazz.beersapi.rest;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.clazz.beersapi.mapper.BeerMapper;
import ar.com.clazz.beersapi.model.Beer;
import ar.com.clazz.beersapi.rest.dto.AddCommentRequest;
import ar.com.clazz.beersapi.rest.dto.CreateBeerRequest;
import ar.com.clazz.beersapi.rest.dto.BeerDto;
import ar.com.clazz.beersapi.rest.dto.UpdateBeerRequest;
import ar.com.clazz.beersapi.service.BeerService;
import lombok.RequiredArgsConstructor;
/**
 * @author Cesar Vargas
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/beers")
public class BeersController {

    private final BeerService beerService;
    private final BeerMapper beerMapper;

    @GetMapping
    public List<BeerDto> getBeers() {
        return beerService.getBeers().stream()
                .map(beer -> beerMapper.toBeerDto(beer))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BeerDto getBeer(@PathVariable String id) {
        Beer beer = beerService.validateAndGetBeer(id);
        return beerMapper.toBeerDto(beer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BeerDto createBeer(@Valid @RequestBody CreateBeerRequest createBeerRequest) {
        Beer beer = beerMapper.toBeer(createBeerRequest);
        beer = beerService.saveBeer(beer);
        return beerMapper.toBeerDto(beer);
    }

    @PutMapping("/{id}")
    public BeerDto updateBeer(@PathVariable String id, @Valid @RequestBody UpdateBeerRequest updateBeerRequest) {
        Beer beer = beerService.validateAndGetBeer(id);
        beerMapper.updateBeerFromDto(updateBeerRequest, beer);
        beer = beerService.saveBeer(beer);
        return beerMapper.toBeerDto(beer);
    }

    @DeleteMapping("/{id}")
    public BeerDto deleteBeer(@PathVariable String id) {
        Beer beer = beerService.validateAndGetBeer(id);
        beerService.deleteBeer(beer);
        return beerMapper.toBeerDto(beer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/comments")
    public BeerDto addBeerComment(@PathVariable String id, @Valid @RequestBody AddCommentRequest addCommentRequest, Principal principal) {
        Beer beer = beerService.validateAndGetBeer(id);
        Beer.Comment comment = new Beer.Comment(principal.getName(), addCommentRequest.getText(), LocalDateTime.now());
        beer.getComments().add(0, comment);
        beer = beerService.saveBeer(beer);
        return beerMapper.toBeerDto(beer);
    }

}
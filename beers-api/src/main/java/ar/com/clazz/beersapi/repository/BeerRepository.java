package ar.com.clazz.beersapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.clazz.beersapi.model.Beer;
/**
 * @author Cesar Vargas
 *
 */
public interface BeerRepository extends MongoRepository<Beer, String> {

}
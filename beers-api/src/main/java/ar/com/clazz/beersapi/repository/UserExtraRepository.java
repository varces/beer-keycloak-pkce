package ar.com.clazz.beersapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.clazz.beersapi.model.UserExtra;
/**
 * @author Cesar Vargas
 *
 */
public interface UserExtraRepository extends MongoRepository<UserExtra, String> {
}

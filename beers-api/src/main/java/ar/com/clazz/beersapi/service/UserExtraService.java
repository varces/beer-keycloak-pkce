package ar.com.clazz.beersapi.service;

import java.util.Optional;

import ar.com.clazz.beersapi.model.UserExtra;

public interface UserExtraService {

    UserExtra validateAndGetUserExtra(String username);

    Optional<UserExtra> getUserExtra(String username);

    UserExtra saveUserExtra(UserExtra userExtra);

}

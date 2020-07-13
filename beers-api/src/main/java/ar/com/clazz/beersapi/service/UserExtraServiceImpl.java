package ar.com.clazz.beersapi.service;

import ar.com.clazz.beersapi.exception.UserExtraNotFoundException;
import ar.com.clazz.beersapi.model.UserExtra;
import ar.com.clazz.beersapi.repository.UserExtraRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserExtraServiceImpl implements UserExtraService {

    private final UserExtraRepository userExtraRepository;

    @Override
    public UserExtra validateAndGetUserExtra(String username) {
        return getUserExtra(username).orElseThrow(() -> new UserExtraNotFoundException(String.format("UserExtra of %s not found", username)));
    }

    @Override
    public Optional<UserExtra> getUserExtra(String username) {
        return userExtraRepository.findById(username);
    }

    @Override
    public UserExtra saveUserExtra(UserExtra userExtra) {
        return userExtraRepository.save(userExtra);
    }

}

package ar.com.clazz.beersapi.rest;

import ar.com.clazz.beersapi.model.UserExtra;
import ar.com.clazz.beersapi.rest.dto.UserExtraRequest;
import ar.com.clazz.beersapi.service.UserExtraService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
/**
 * @author Cesar Vargas
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userextras")
public class UserExtraController {

    private final UserExtraService userExtraService;

    @GetMapping("/me")
    public UserExtra getUserExtra(Principal principal) {
        return userExtraService.validateAndGetUserExtra(principal.getName());
    }

    @PostMapping("/me")
    public UserExtra saveUserExtra(@Valid @RequestBody UserExtraRequest updateUserExtraRequest, Principal principal) {
        Optional<UserExtra> userExtraOptional = userExtraService.getUserExtra(principal.getName());
        UserExtra userExtra = userExtraOptional.orElseGet(() -> new UserExtra(principal.getName()));
        userExtra.setAvatar(updateUserExtraRequest.getAvatar());
        return userExtraService.saveUserExtra(userExtra);
    }

}

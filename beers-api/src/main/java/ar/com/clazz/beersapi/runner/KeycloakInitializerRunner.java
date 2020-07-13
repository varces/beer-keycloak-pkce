package ar.com.clazz.beersapi.runner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.com.clazz.beersapi.security.WebSecurityConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeycloakInitializerRunner implements CommandLineRunner {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    private final Keycloak keycloakAdmin;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing '{}' realm in Keycloak ...", REALM_NAME);

        Optional<RealmRepresentation> representationOptional = keycloakAdmin.realms().findAll().stream()
                .filter(r -> r.getRealm().equals(REALM_NAME)).findAny();
        if (representationOptional.isPresent()) {
            log.info("Removing already pre-configured '{}' realm", REALM_NAME);
            keycloakAdmin.realm(REALM_NAME).remove();
        }

        // Realm
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(REALM_NAME);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(true);

        // Client
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(BEERS_APP_CLIENT_ID);
        clientRepresentation.setDirectAccessGrantsEnabled(true);
        clientRepresentation.setDefaultRoles(new String[]{BEERS_APP_ROLES.get(0)});
        clientRepresentation.setPublicClient(true);
        clientRepresentation.setRedirectUris(Collections.singletonList(BEERS_APP_REDIRECT_URL));
        realmRepresentation.setClients(Collections.singletonList(clientRepresentation));

        // Users
        List<UserRepresentation> userRepresentations = BEERS_APP_USERS.stream().map(userPass -> {
            // Client roles
            Map<String, List<String>> clientRoles = new HashMap<>();
            if ("admin".equals(userPass.getUsername())) {
                clientRoles.put(BEERS_APP_CLIENT_ID, BEERS_APP_ROLES);
            } else {
                clientRoles.put(BEERS_APP_CLIENT_ID, Collections.singletonList(BEERS_APP_ROLES.get(0)));
            }

            // User Credentials
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userPass.getPassword());

            // User
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(userPass.getUsername());
            userRepresentation.setEnabled(true);
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
            userRepresentation.setClientRoles(clientRoles);

            return userRepresentation;
        }).collect(Collectors.toList());
        realmRepresentation.setUsers(userRepresentations);

        // Create Realm
        keycloakAdmin.realms().create(realmRepresentation);

        // Testing
        UserPass admin = BEERS_APP_USERS.get(0);
        log.info("Testing getting token for '{}' ...", admin.getUsername());

        Keycloak keycloakMovieApp = KeycloakBuilder.builder().serverUrl(keycloakServerUrl)
                .realm(REALM_NAME).username(admin.getUsername()).password(admin.getPassword())
                .clientId(BEERS_APP_CLIENT_ID).build();

        log.info("'{}' token: {}", admin.getUsername(), keycloakMovieApp.tokenManager().grantToken().getToken());
        log.info("'{}' initialization completed successfully!", REALM_NAME);
    }

    private static final String REALM_NAME = "beer-services";
    private static final String BEERS_APP_CLIENT_ID = "beer-app";
    private static final List<String> BEERS_APP_ROLES = Arrays.asList(WebSecurityConfig.USER,
            WebSecurityConfig.MANAGER);
    private static final String BEERS_APP_REDIRECT_URL = "http://localhost:3000/*";
    private static final List<UserPass> BEERS_APP_USERS = Arrays.asList(new UserPass("admin", "admin"),
            new UserPass("user", "user"));

    @Data
    @AllArgsConstructor
    private static class UserPass {
        private String username;
        private String password;
    }

}
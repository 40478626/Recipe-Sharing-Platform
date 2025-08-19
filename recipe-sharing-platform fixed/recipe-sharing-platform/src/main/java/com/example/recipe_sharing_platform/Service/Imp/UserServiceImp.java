package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.User;
import com.example.recipe_sharing_platform.Repository.UserRepo;
import com.example.recipe_sharing_platform.Request.LoginRequest;
import com.example.recipe_sharing_platform.Request.LogoutRequest;
import com.example.recipe_sharing_platform.Request.UserRequest;
import com.example.recipe_sharing_platform.Response.LoginResponse;
import com.example.recipe_sharing_platform.Service.UserService;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.grant-type:client_credentials}")
    private String grantType;

    @Override
    public User registerUser(UserRequest userRequest) {
        // Save the user to the local database
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());

        // Create the user in Keycloak
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(user.getUsername());
        userRepresentation.setLastName(user.getUsername());
        userRepresentation.setRealmRoles(Collections.singletonList(user.getRole()));

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);

        userRepresentation.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm(realm).users().create(userRepresentation);
        int status = response.getStatus();

        if (status != 201) {
            String responseBody = response.readEntity(String.class);
            throw new RuntimeException("Failed to create user in Keycloak. Status: " + status + ". Response: " + responseBody);
        }

        // Step 5: Retrieve the Keycloak userId
        String location = response.getHeaderString("Location");
        if (location == null) {
            throw new RuntimeException("Failed to retrieve Keycloak userId. No location header returned.");
        }
        String keycloakUserId = location.substring(location.lastIndexOf("/") + 1);

        // Step 6: Update local user with Keycloak userId
        user.setId(keycloakUserId);
        userRepo.save(user);

        return user;
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        try {
            // Step 1: Authenticate the user with Keycloak
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakServerUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .grantType(grantType)
                    .username(loginRequest.getUsername())
                    .password(loginRequest.getPassword())
                    .build();

            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

            // Step 2: Fetch the Keycloak userId
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(loginRequest.getUsername());
            if (users.isEmpty()) {
                throw new RuntimeException("User not found in Keycloak.");
            }
            String keycloakUserId = users.get(0).getId(); // Assuming username is unique

            // Step 3: Build and return the login response
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(tokenResponse.getToken());
            loginResponse.setExpiresIn(tokenResponse.getExpiresIn());
            loginResponse.setUserId(keycloakUserId); // Include the userId in the response

            return loginResponse;

        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<?> logoutUser(LogoutRequest logoutRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String logoutEndpoint = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/logout";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token", logoutRequest.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            restTemplate.exchange(logoutEndpoint, HttpMethod.POST, request, String.class);
            return ResponseEntity.ok("User logged out successfully");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}

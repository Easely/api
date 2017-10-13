package com.shepherdjerred.easely.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepherdjerred.easely.api.controller.payload.LoginPayload;
import com.shepherdjerred.easely.api.controller.payload.PostLoginPayload;
import com.shepherdjerred.easely.api.controller.payload.RegisterPayload;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.storage.Store;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;

import java.io.UnsupportedEncodingException;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import static spark.Spark.post;

@Log4j2
public class UserController implements Controller {
    private Store store;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public UserController(Store store) {
        this.store = store;
    }

    @Override
    public void setupRoutes() {
        post("/api/user/login", (request, response) -> {
            response.type("application/json");

            LoginPayload loginPayload = objectMapper.readValue(request.body(), LoginPayload.class);

            if (!loginPayload.isValid()) {
                response.status(400);
                return "";
            }

            UUID userUuid = store.getUserUuid(loginPayload.getEmail());
            Optional<User> userToAuthTo = store.getUser(userUuid);

            if (userToAuthTo.isPresent()) {
                if (userToAuthTo.get().authenticate(loginPayload.getPassword())) {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder();
                        Algorithm algorithm = Algorithm.HMAC256(processBuilder.environment().get("JWT_SECRET"));
                        String token = JWT.create()
                                .withIssuer("https://easely.shepherdjerred.com")
                                .withClaim("email", loginPayload.getEmail())
                                .withClaim("uuid", String.valueOf(userUuid))
                                .withClaim("easelUsername", userToAuthTo.get().getEaselUsername())
                                .sign(algorithm);
                        return objectMapper.writeValueAsString(new PostLoginPayload(token));
                    } catch (UnsupportedEncodingException | JWTCreationException exception) {
                        response.status(500);
                        return objectMapper.writeValueAsString(exception.getMessage());
                    }
                }

                response.status(401);
                return "";
            }
            response.status(404);
            return "";
        });

        post("/api/user/register", (request, response) -> {
            response.type("application/json");

            RegisterPayload registerPayload = objectMapper.readValue(request.body(), RegisterPayload.class);

            if (!registerPayload.isValid()) {
                response.status(400);
                return "";
            }

            User user = new User(
                    UUID.randomUUID(),
                    registerPayload.getEmail(),
                    BCrypt.hashpw(registerPayload.getPassword(), BCrypt.gensalt()),
                    registerPayload.getEaselUsername(),
                    registerPayload.getEaselPassword(),
                    EnumSet.noneOf(User.Permission.class)
            );

            store.addUser(user);

            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                Algorithm algorithm = Algorithm.HMAC256(processBuilder.environment().get("JWT_SECRET"));
                String token = JWT.create()
                        .withIssuer("https://easely.shepherdjerred.com")
                        .withClaim("email", registerPayload.getEmail())
                        .withClaim("uuid", String.valueOf(user.getUuid()))
                        .withClaim("easelUsername", user.getEaselUsername())
                        .sign(algorithm);
                return objectMapper.writeValueAsString(new PostLoginPayload(token));
            } catch (UnsupportedEncodingException | JWTCreationException exception) {
                response.status(500);
                return objectMapper.writeValueAsString(exception.getMessage());
            }
        });
    }

}

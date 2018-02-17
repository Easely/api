package com.shepherdjerred.easely.api.router;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.controller.UserController;
import com.shepherdjerred.easely.api.controller.payload.LoginPayload;
import com.shepherdjerred.easely.api.controller.payload.PostLoginPayload;
import com.shepherdjerred.easely.api.controller.payload.RegisterPayload;
import com.shepherdjerred.easely.api.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.security.auth.login.FailedLoginException;
import java.io.UnsupportedEncodingException;

import static spark.Spark.post;

@Log4j2
@AllArgsConstructor
public class UserRouter implements Router {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private UserController userController;
    private EaselyConfig easelyConfig;

    @Override
    public void setupRoutes() {
        post("/api/user/login", (request, response) -> {
            log.debug("Attempting to login");
            response.type("application/json");

            LoginPayload loginPayload = objectMapper.readValue(request.body(), LoginPayload.class);

            if (!loginPayload.isValid()) {
                response.status(400);
                log.error("Payload not valid");
                // TODO
                return "";
            }

            User user;
            try {
                user = userController.login(loginPayload.getEmail(), loginPayload.getPassword());
            } catch (FailedLoginException e) {
                log.error(e);
                // TODO
                return "";
            }

            try {
                Algorithm algorithm = Algorithm.HMAC256(easelyConfig.getJwtSecret());
                String token = JWT.create()
                        .withIssuer(easelyConfig.getJwtIssuer())
                        .withClaim("email", loginPayload.getEmail())
                        .withClaim("uuid", String.valueOf(user.getUuid()))
                        .withClaim("easelUsername", user.getEaselUsername())
                        .sign(algorithm);
                return new PostLoginPayload(token);
            } catch (UnsupportedEncodingException | JWTCreationException exception) {
                response.status(500);
                return objectMapper.writeValueAsString(exception.getMessage());
            }
        }, new JsonTransformer());

        post("/api/user/register", (request, response) -> {
            response.type("application/json");

            RegisterPayload registerPayload = objectMapper.readValue(request.body(), RegisterPayload.class);

            if (!registerPayload.isValid()) {
                response.status(400);
                return "";
            }

            User user = userController.register(registerPayload.getEmail(), registerPayload.getPassword(), registerPayload.getEaselUsername(), registerPayload.getEaselPassword());

            try {
                Algorithm algorithm = Algorithm.HMAC256(easelyConfig.getJwtSecret());
                String token = JWT.create()
                        .withIssuer(easelyConfig.getJwtIssuer())
                        .withClaim("email", registerPayload.getEmail())
                        .withClaim("uuid", String.valueOf(user.getUuid()))
                        .withClaim("easelUsername", user.getEaselUsername())
                        .sign(algorithm);
                return objectMapper.writeValueAsString(new PostLoginPayload(token));
            } catch (UnsupportedEncodingException | JWTCreationException exception) {
                response.status(500);
                return objectMapper.writeValueAsString(exception.getMessage());
            }

        }, new JsonTransformer());
    }
}

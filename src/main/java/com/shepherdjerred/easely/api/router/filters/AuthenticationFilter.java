package com.shepherdjerred.easely.api.router.filters;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.storage.Store;
import lombok.extern.log4j.Log4j2;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

import static spark.Spark.halt;

@Log4j2
public class AuthenticationFilter implements Filter {

    private Store store;
    private String jwtSecret;

    public AuthenticationFilter(Store store) {
        this.store = store;
        // TODO make this an env var
        jwtSecret = new ProcessBuilder().environment().get("JWT_SECRET");
    }

    @Override
    public void handle(Request request, Response response) {
        log.debug("Attempting to validate request");
        DecodedJWT jwt;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            // TODO issuer should be an env var
            String issuer = "http://easely.shepherdjerred.com";
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            jwt = verifier.verify(request.headers("Authorization").replace("Bearer ", ""));
        } catch (JWTVerificationException e) {
            log.error(e);
            throw halt(401);
        } catch (UnsupportedEncodingException e) {
            log.error(e);
            throw halt(500);
        }
        Optional<User> user = store.getUser(UUID.fromString(jwt.getClaim("uuid").asString()));
        log.debug("User validated: " + user.get().getEmail());
        request.attribute("user", user.get());
    }
}

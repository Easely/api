package com.shepherdjerred.easely.api.controller.payload;

import lombok.Getter;

public class PostLoginPayload implements Payload {

    @Getter
    private final String jsonWebToken;

    public PostLoginPayload(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}

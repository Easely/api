package com.shepherdjerred.easely.api.controller.payload;
import lombok.Getter;
import lombok.Setter;

public class RegisterPayload implements Payload {

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String easelUsername;
    @Getter
    @Setter
    private String easelPassword;


    @Override
    public boolean isValid() {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }
}
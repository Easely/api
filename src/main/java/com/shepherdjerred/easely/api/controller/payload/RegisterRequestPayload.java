package com.shepherdjerred.easely.api.controller.payload;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;

public class RegisterRequestPayload implements Payload {

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

    // TODO provide message with what went wrong
    @Override
    public boolean isValid() {
        if (!EmailValidator.getInstance().isValid(email)) {
            return false;
        }
        if (!isHardingDomain()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    private boolean isHardingDomain() {
        String domain;
        String[] splitEmail = email.split("@");
        domain = splitEmail[1];
        return domain.toLowerCase().equals("harding.edu");
    }
}

package com.shepherdjerred.easely.api.object;

import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Getter
    private final UUID uuid;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String hashedPassword;
    @Getter
    @Setter
    private String easelUsername;
    @Getter
    @Setter
    private String easelPassword;

    public boolean authenticate(String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}

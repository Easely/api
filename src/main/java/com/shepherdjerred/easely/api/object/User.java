package com.shepherdjerred.easely.api.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

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
    private String easelUsername;
    @Getter
    @Setter
    private String easelPassword;
    @Getter
    @Setter
    private String hashedPassword;

    public User(UUID uuid, String email, String easelUsername, String easelPassword, String hashedPassword) {
        this.uuid = uuid;
        this.email = email;
        this.easelUsername = easelUsername;
        this.easelPassword = easelPassword;
        this.hashedPassword = hashedPassword;
    }

    public boolean authenticate(String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}

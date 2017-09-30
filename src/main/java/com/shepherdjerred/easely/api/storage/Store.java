package com.shepherdjerred.easely.api.storage;

import com.shepherdjerred.easely.api.object.User;

import java.util.Optional;
import java.util.UUID;

public interface Store {

    void addUser(User user);
    Optional<User> getUser(UUID uuid);
    boolean isEmailTaken(String username);
    UUID getUserUuid(String name);

}

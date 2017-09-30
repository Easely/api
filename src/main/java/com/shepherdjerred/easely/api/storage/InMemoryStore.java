package com.shepherdjerred.easely.api.storage;

import com.shepherdjerred.easely.api.object.User;

import java.util.Optional;
import java.util.UUID;

// TODO implement methods
public class InMemoryStore implements Store {
    @Override
    public void addUser(User user) {

    }

    @Override
    public Optional<User> getUser(UUID uuid) {
        return null;
    }

    @Override
    public boolean isEmailTaken(String username) {
        return false;
    }

    @Override
    public UUID getUserUuid(String name) {
        return null;
    }
}

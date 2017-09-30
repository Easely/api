package com.shepherdjerred.easely.api.storage.mysql;

import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.storage.Store;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.UUID;

@Log4j2
public class MysqlStore implements Store {

    @Getter
    private final Database database;
    private final UserDAO userDAO;

    @Override
    public void addUser(User user) {

    }

    @Override
    public Optional<User> getUser(UUID uuid) {
        return null;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return false;
    }

    @Override
    public UUID getUserUuid(String name) {
        return null;
    }
}

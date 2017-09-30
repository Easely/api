package com.shepherdjerred.easely.api.storage.dao;

import com.shepherdjerred.easely.api.object.User;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {
    Optional<User> select(String name);

    Optional<User> select(UUID uuid);

    Collection<User> select();

    void insert(User user);

    void drop(User user);

    void update(User type);
}

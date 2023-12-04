package com.sina.usermanagement.user.repository;

import com.sina.usermanagement.user.entity.User;
import com.sina.usermanagement.user.entity.UserEntityFields;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
    public User findByUserName(String userName) {
        return find(UserEntityFields.USER_NAME, userName).firstResult();
    }

    public User findByEmail(String email) {
        return find(UserEntityFields.EMAIL, email).firstResult();
    }
}

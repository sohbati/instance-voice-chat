package com.sina.usermanagement.user.repository;

import com.sina.usermanagement.user.entity.User;
import com.sina.usermanagement.user.entity.UserEntityFields;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
    public User findByUserName(String userName) {
        return find(UserEntityFields.USER_NAME, userName).firstResult();
    }
    public User findByUserId(String userId) {
        ObjectId id = new ObjectId(userId);
        return findById(id);
    }

    public User findByEmail(String email) {
        return find(UserEntityFields.EMAIL, email).firstResult();
    }
}

package com.sina.usermanagement.user.entity;

import com.sina.usermanagement.user.enumeration.UserGenderEnum;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@MongoEntity(collection = "AppUser")
public class User {
    private ObjectId id;

    @BsonProperty(UserEntityFields.USER_NAME)
    private String userName;
    @BsonProperty(UserEntityFields.FULL_NAME)
    private String fullName;
    @BsonProperty(UserEntityFields.EMAIL)
    private String email;
    @BsonProperty(UserEntityFields.GENDER)
    private UserGenderEnum gender;
}

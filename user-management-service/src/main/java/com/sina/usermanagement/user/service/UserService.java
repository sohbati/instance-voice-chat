package com.sina.usermanagement.user.service;

import com.sina.usermanagement.user.api.record.QickReqisterUserRequestRecord;
import com.sina.usermanagement.user.api.record.UserResponseRecord;
import com.sina.usermanagement.user.enumeration.UserGenderEnum;
import com.sina.usermanagement.infrastructure.exception.ApplicationException;
import com.sina.usermanagement.user.api.record.UserRequestRecord;
import com.sina.usermanagement.user.entity.User;
import com.sina.usermanagement.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseRecord getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            ApplicationException.userNotFoundException(userId);
        }
        return map(user);
    }

    public String quickSave(QickReqisterUserRequestRecord userRequestRecord) {
        validateUserPreExistForNewUserName(userRequestRecord.userName());
        User user = new User();
        user.setUserName(userRequestRecord.userName());
        user.setGender(UserGenderEnum.valueOf(userRequestRecord.gender().toUpperCase()));

        return save(user);
    }
    public String save(UserRequestRecord userRequestRecord) {
        validateUserPreExistForNewUserName(userRequestRecord.userName());
        validateUserPreExistForNewUserEmail(userRequestRecord.email());

        User user = map(userRequestRecord);
        return save(user);
    }

    private String save(User user) {
        userRepository.persist(user);
        return user.getId().toString();
    }

    private void validateUserPreExistForNewUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            ApplicationException.userNameAlreadyExist(userName);
        }
    }
    private void validateUserPreExistForNewUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            ApplicationException.emailAlreadyExists(email);
        }
    }

    private UserResponseRecord map(User user) {
        return UserResponseRecord.builder()
                .id(user.getId().toString())
                .userName(user.getUserName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .gender(user.getGender().name()).build();
    }

    private User map(UserRequestRecord userRequestRecord) {
        User user = new User();
        user.setUserName(userRequestRecord.userName());
        user.setFullName(userRequestRecord.fullName());
        user.setEmail(userRequestRecord.email());
        user.setGender(UserGenderEnum.valueOf(userRequestRecord.gender().toUpperCase()));
        return user;
    }
}

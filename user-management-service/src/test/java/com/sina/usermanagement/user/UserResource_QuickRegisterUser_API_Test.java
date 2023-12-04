package com.sina.usermanagement.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sina.usermanagement.TestBase;
import com.sina.usermanagement.infrastructure.exception.ErrorRecord;
import com.sina.usermanagement.user.api.record.UserResponseRecord;
import com.sina.usermanagement.user.enumeration.UserErrorCodeEnum;
import io.quarkus.runtime.util.StringUtil;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import jakarta.ws.rs.core.Response;
import org.acme.mongodb.panache.MongoDbResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.config.LogConfig.logConfig;

@QuarkusTest
@QuarkusTestResource(MongoDbResource.class)
public class UserResource_QuickRegisterUser_API_Test extends TestBase {
    @BeforeAll
    public static void initAll() {
        initRestAssured();
    }

    @Test
    public void testSaveUser_happyPass() {
        testSaveUser_happyPassImpl();
    }

    @Test
    public void testSaveUser_username_nullValue_test() {
        testSaveUser_username_nullValue_testImpl();
    }

    @Test
    public void testSaveUser_username_emptyValue_test() {
        testSaveUser_username_emptyValue_testImpl();
    }

    @Test
    public void testSaveUser_username_invalidCharacter_test() {
        testSaveUser_username_invalidCharacter_testImpl();
    }

    @Test
    public void testSaveUser_username_shortLength_test() {
        testSaveUser_username_shortLength_testImpl();
    }

    @Test
    public void testSaveUser_username_lengthTooLong_test() {
        testSaveUser_username_lengthTooLong_testImpl();
    }

    @Test
    public void testSaveUser_gender_empty_value_test() {
        testSaveUser_gender_empty_value_testImpl();
    }

    @Test
    public void testSaveUser_gender_invalid_value_test() {
        testSaveUser_gender_invalid_value_testImpl();
    }

    @Test
    public void testSaveUser_duplicate_username() {
        testSaveUser_duplicate_usernameImpl();
    }

    public void testSaveUser_happyPassImpl() {
        String fakeUniqueUserName = "user1";
        String user = "{\n" +
                "    \"userName\": \"" + fakeUniqueUserName +"\",\n" +
                "    \"gender\": \"male\"\n" +
                "}";
        UserResponseRecord response = restWithOk(user);
        Assertions.assertThat(StringUtil.isNullOrEmpty(response.id()) == false);
    }

    void testSaveUser_username_nullValue_testImpl() {
        String user = "{\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.VALUE_IS_NULL_OR_EMPTY));
    }

    void testSaveUser_username_emptyValue_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"\",\n" +
                "    \"gender\": \"male\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.VALUE_IS_NULL_OR_EMPTY));
    }

    void testSaveUser_username_invalidCharacter_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"username@\",\n" +
                "    \"gender\": \"male\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USERNAME_CONTAINS_INVALID_CHARACTERS));
    }

    void testSaveUser_username_shortLength_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"shrt\",\n" +
                "    \"gender\": \"male\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USER_NAME_LENGTH_NOT_FIT));
    }

    void testSaveUser_username_lengthTooLong_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"the_very_long_user_name_chosen\",\n" +
                "    \"gender\": \"male\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USER_NAME_LENGTH_NOT_FIT));
    }


    @Test
    void testSaveUser_gender_null_value_test() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.GENDER_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_gender_empty_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"gender\": \"\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.GENDER_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_gender_invalid_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"gender\": \"M\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.FULL_NAME_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_duplicate_usernameImpl() {
        String user1 = "{\n" +
                "    \"userName\": \"lowrance\",\n" +
                "    \"gender\": \"female\"\n" +
                "}";
        String user2 = "{\n" +
                "    \"userName\": \"lowrance\",\n" +
                "    \"gender\": \"female\"\n" +
                "}";
        restWithOk(user1);
        ErrorRecord error = restWith400(user2);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USER_NAME_ALREADY_EXISTS));
    }

    private UserResponseRecord restWithOk(String json) {
        String path = "/users/quick-register";
        UserResponseRecord response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(path)
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .body().as(UserResponseRecord.class);
        return response;
    }

    private ErrorRecord restWith400(String json) {
        String path = "/users/quick-register";
        ErrorRecord response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(path)
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .extract()
                .body().as(ErrorRecord.class);
        return response;
    }
}

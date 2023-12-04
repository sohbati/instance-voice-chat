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
public class UserResource_userinfo_API_Test extends UserTestBase {
    @BeforeAll
    public static void initAll() {
        initRestAssured();
    }

    @Test
    public void testSaveUser_happyPass() {
        testSaveUser_happyPassImpl();
    }

    public void testSaveUser_happyPassImpl() {
        /**
        * 1- register a user
        * 2- fetch user with /user-info
        * 3- assert
        */
        int sequence = getNewInt();
        testSaveUser_RegisterCompleteUserWithOkResponse(sequence);
        String userName = "user" + sequence;

        UserResponseRecord response = restWithOk(userName);
        Assertions.assertThat(response.userName()).isEqualTo(userName);
        Assertions.assertThat(response.id()).isNotNull();
        Assertions.assertThat(response.fullName()).isNotBlank();
        Assertions.assertThat(response.gender()).isNotBlank();
        Assertions.assertThat(response.email()).isNotNull();
    }


    private UserResponseRecord restWithOk(String userName) {
        String path = "/users/" + userName + "/user-info";
        UserResponseRecord response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .body().as(UserResponseRecord.class);
        return response;
    }

    private ErrorRecord restWith400(String userName) {
        String path = "/users/" + userName + "/user-info";
        ErrorRecord response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .extract()
                .body().as(ErrorRecord.class);
        return response;
    }
}

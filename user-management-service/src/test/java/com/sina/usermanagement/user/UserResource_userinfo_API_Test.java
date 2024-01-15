package com.sina.usermanagement.user;

import com.sina.usermanagement.infrastructure.exception.ErrorRecord;
import com.sina.usermanagement.user.api.record.UserResponseRecord;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import com.sina.usermanagement.infrastructure.mongodb.MongoDbResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        String userId = testSaveUser_RegisterCompleteUserWithOkResponse(sequence);
        String userName = "user" + sequence;

        UserResponseRecord response = getUserInfoRestCallWithOkResponse(userId);
        Assertions.assertThat(response.userName()).isEqualTo(userName);
        Assertions.assertThat(response.id()).isNotNull();
        Assertions.assertThat(response.fullName()).isNotBlank();
        Assertions.assertThat(response.gender()).isNotBlank();
        Assertions.assertThat(response.email()).isNotNull();
    }


    private UserResponseRecord getUserInfoRestCallWithOkResponse(String userId) {
        String path = "/users/" + userId + "/user-info";
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

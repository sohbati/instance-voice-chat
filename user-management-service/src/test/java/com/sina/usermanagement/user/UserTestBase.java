package com.sina.usermanagement.user;

import com.sina.usermanagement.TestBase;
import com.sina.usermanagement.user.api.record.UserRequestRecord;
import com.sina.usermanagement.user.api.record.UserResponseRecord;
import io.quarkus.runtime.util.StringUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.assertj.core.api.Assertions;

import java.util.concurrent.atomic.AtomicInteger;

public class UserTestBase extends TestBase {

    protected static AtomicInteger sequencer = new AtomicInteger(0);

    protected int getNewInt() {
        int sequence = sequencer.getAndIncrement();
        System.out.println("Sequence:" + sequence);
        return sequence;
    }
    protected String testSaveUser_RegisterCompleteUserWithOkResponse(int sequence) {
        UserRequestRecord user  = UserRequestRecord.builder()
                .userName("user" + sequence)
                .fullName("user" + sequence)
                .gender("male")
                .email("email" + sequence + "@gmail.com")
                .build();

        UserResponseRecord response = restToRegisterUserWith_200_OK_Response(jsonString(user));
        Assertions.assertThat(StringUtil.isNullOrEmpty(response.id())).isFalse();
        return response.id();
    }

    protected UserResponseRecord restToRegisterUserWith_200_OK_Response(String json) {
        String path = "/users/register";
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
}

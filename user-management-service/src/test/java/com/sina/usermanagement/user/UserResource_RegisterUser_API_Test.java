package com.sina.usermanagement.user;

import com.sina.usermanagement.infrastructure.exception.ErrorRecord;
import com.sina.usermanagement.user.enumeration.UserErrorCodeEnum;
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
public class UserResource_RegisterUser_API_Test extends UserTestBase {
    @BeforeAll
    public static void initAll() {
        initRestAssured();
    }

    @Test
    public void testRegisterUser_happyPass() {
        testSaveUser_RegisterCompleteUserWithOkResponse(getNewInt());
    }

    @Test
    public void testRegisterUser_invalidEmail_AtSignMissed_test() {
        testSaveUser_invalidEmail_AtSignMissed_testImpl();
    }

    @Test
    public void testRegisterUser_invalidEmail_dotMissed_test() {
        testSaveUser_invalidEmail_dotMissed_testImpl();
    }

    @Test
    public void testRegisterUser_invalidEmail_nullValue_test() {
        testSaveUser_invalidEmail_nullValue_testImpl();
    }

    @Test
    public void testRegisterUser_invalidEmail_emptyValue_test() {
        testSaveUser_invalidEmail_emptyValue_testImpl();
    }

    @Test
    public void testRegisterUser_username_nullValue_test() {
        testSaveUser_username_nullValue_testImpl();
    }

    @Test
    public void testRegisterUser_username_emptyValue_test() {
        testSaveUser_username_emptyValue_testImple();
    }

    @Test
    public void testRegisterUser_username_invalidCharacter_test() {
        testSaveUser_username_invalidCharacter_testImpl();
    }

    @Test
    public void testRegisterUser_username_shortLength_test() {
        testSaveUser_username_shortLength_testImpl();
    }

    @Test
    public void testRegisterUser_username_lengthTooLong_test() {
        testSaveUser_username_lengthTooLong_testImpl();
    }

    @Test
    public void testRegisterUser_fullname_null_value_test() {
        testSaveUser_fullname_null_value_testImpl();
    }

    @Test
    public void testRegisterUser_fullname_empty_value_test() {
        testSaveUser_fullname_empty_value_testImpl();
    }

    @Test
    public void testRegisterUser_gender_null_value_test() {
        testSaveUser_gender_null_value_testImpl();
    }

    @Test
    public void testRegisterUser_gender_empty_value_test() {
        testSaveUser_gender_empty_value_testImpl();
    }

    @Test
    public void testRegisterUser_gender_invalid_value_test() {
        testSaveUser_gender_invalid_value_testImpl();
    }

    @Test
    public void testRegisterUser_duplicate_username() {
        testSaveUser_duplicate_usernameImpl();
    }

    @Test
    public void testRegisterUser_duplicate_email() {
        testSaveUser_duplicate_emailImpl();
    }



    void testSaveUser_invalidEmail_AtSignMissed_testImpl() {
        int sequence = getNewInt();
        String user = "{\n" +
                "    \"userName\": \"person" + sequence + "\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"emailgmail.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.EMAIL_IS_NOT_VALID));
    }

    void testSaveUser_invalidEmail_dotMissed_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"person5\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@gmailcom\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.EMAIL_IS_NOT_VALID));
    }

    void testSaveUser_invalidEmail_nullValue_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"person5\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.EMAIL_IS_NOT_VALID));
    }

    void testSaveUser_invalidEmail_emptyValue_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"person5\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.EMAIL_IS_NOT_VALID));
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

    void testSaveUser_username_emptyValue_testImple() {
        String user = "{\n" +
                "    \"userName\": \"\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.VALUE_IS_NULL_OR_EMPTY));
    }

    void testSaveUser_username_invalidCharacter_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"username@\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USERNAME_CONTAINS_INVALID_CHARACTERS));
    }

    void testSaveUser_username_shortLength_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"shrt\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USER_NAME_LENGTH_NOT_FIT));
    }

    void testSaveUser_username_lengthTooLong_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"the_very_long_user_name_chosen\",\n" +
                "    \"fullName\": \"person personal\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USER_NAME_LENGTH_NOT_FIT));
    }

    void testSaveUser_fullname_null_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.FULL_NAME_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_fullname_empty_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"fullName\": \"\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.FULL_NAME_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_gender_null_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"fullName\": \"fullname\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.GENDER_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_gender_empty_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"fullName\": \"\",\n" +
                "    \"gender\": \"\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.GENDER_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_gender_invalid_value_testImpl() {
        String user = "{\n" +
                "    \"userName\": \"john_norman\",\n" +
                "    \"fullName\": \"leonardo fully\",\n" +
                "    \"gender\": \"M\",\n" +
                "    \"email\": \"email@test.com\"\n" +
                "}";
        ErrorRecord error = restWith400(user);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.FULL_NAME_SHOULD_NOT_BE_EMPTY));
    }

    void testSaveUser_duplicate_usernameImpl() {
        int sequence = getNewInt();
        int sequence2 = getNewInt();
        String user1 = "{\n" +
                "    \"userName\": \"user" + sequence + "\",\n" +
                "    \"fullName\": \"lowrance \",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"email\": \"lowrance" + sequence + "@test.com\"\n" +
                "}";
        String user2 = "{\n" +
                "    \"userName\": \"user" + sequence + "\",\n" +
                "    \"fullName\": \"lowrance \",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"email\": \"lowrance" + sequence2 + "@test.com\"\n" +
                "}";
        restToRegisterUserWith_200_OK_Response(user1);
        ErrorRecord error = restWith400(user2);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.USER_NAME_ALREADY_EXISTS));
    }

    void testSaveUser_duplicate_emailImpl() {
        int sequence = getNewInt();
        int sequence2 = getNewInt();

        String user1 = """ 
                    {
                    "userName": "lowrance%s",
                    "fullName": "lowrance",
                    "gender": "female", 
                    "email": "lowrance%s@gmail.com"
                    }
                """.formatted(sequence, sequence);
        String user2 = """ 
                    {
                    "userName": "lowrance%s",
                    "fullName": "lowrance",
                    "gender": "female",
                    "email": "lowrance%s@gmail.com"
                    }
                """.formatted(sequence2, sequence);
        restToRegisterUserWith_200_OK_Response(user1);
        ErrorRecord error = restWith400(user2);
        Assertions.assertThat(error.errorCode().equals(UserErrorCodeEnum.EMAIL_ALREADY_EXISTS));
    }

    private ErrorRecord restWith400(String json) {
        String path = "/users/register";
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

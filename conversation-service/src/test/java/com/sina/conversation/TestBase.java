package com.sina.conversation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.parsing.Parser;

import static io.restassured.config.LogConfig.logConfig;

public class TestBase {
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected static void initRestAssured() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config
                .logConfig((logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> new ObjectMapper()
                        .registerModule(new Jdk8Module())
                        .registerModule(new JavaTimeModule())
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)));
    }

//    protected String jsonString(UserRequestRecord userRequest) {
//        try {
//            return objectMapper.writeValueAsString(userRequest);
//        } catch (JsonProcessingException e) {
//            System.err.println( "Error in converting to json -> userRequest = " + userRequest);
//            throw new RuntimeException(e);
//        }
//    }
}

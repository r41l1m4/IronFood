package com.ironia.ironfood.courier.manager.api.controller;

import com.ironia.ironfood.courier.manager.domain.model.Courier;
import com.ironia.ironfood.courier.manager.domain.repository.CourierRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourierControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CourierRepository courierRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
        RestAssured.basePath = "/api/v1/couriers";
    }

    @Test
    void shouldReturn201() {
        String requestBody = """
                {
                    "name": "João da Silva",
                    "phone": "11973653734"
                }
            """;

        RestAssured
                .given()
                    .body(requestBody)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", Matchers.notNullValue())
                    .body("name", Matchers.equalTo("João da Silva"));
    }

    @Test
    void shouldReturn200() {
        UUID courierId = courierRepository.saveAndFlush(
                Courier.brandNew("Maria das Desolações", "11836359430")
        ).getId();

        RestAssured
                .given()
                    .pathParam("courierId", courierId)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{courierId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", Matchers.equalTo(courierId.toString()))
                    .body("name", Matchers.equalTo("Maria das Desolações"))
                    .body("phone", Matchers.equalTo("11836359430"));
    }

}
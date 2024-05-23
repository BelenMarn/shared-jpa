package com.autentia.sharedjpa.restController.integrationTest;


import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.primaryAdapter.request.FriendRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.Argument;
import org.flywaydb.core.Flyway;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class RestFriendControllerIT {
    private final long id = 90;
    private final String name = "Test";

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("simple_shared")
            .withUsername("root")
            .withPassword("root")
            //.withInitScript()
            .withExposedPorts(3306);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void doBeforeAll(@Autowired Flyway flyway, @LocalServerPort int port) {
        RestAssured.baseURI = "http://localhost:" + port;
        flyway.migrate();
    }

    @BeforeEach
    void beforeEach() {
        given()
                .contentType(ContentType.JSON)
                .body(new FriendRequest(id, name))
                .when()
                .post("/rest/friend")
                .then()
                .statusCode(200);

    }

    @Test
    @Order(1)
    public void shouldGetAllFriendsAndReturnStatus200() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/rest/friend")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void shouldFindFriendByIdAndReturnStatus200() {

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 4)
                .when()
                .get("/rest/friend/{id}")
                .then()
                .statusCode(200)
                .body("idFriend",equalTo(4))
                .body("name",equalTo("Ismael"));
    }

    @Test
    @Order(3)
    public void shouldReturnFriendNotFoundExceptionWhenFriendNotFound() {

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 90)
                .when()
                .get("/rest/friend/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    public void shouldUpdateFriendAndReturnStatus200(){

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .queryParam("name", "test")
                .when()
                .put("/rest/friend/{id}")
                .then()
                .statusCode(200);

    }


    @Test
    @Order(5)
    public void
    shouldDeleteFriendAndReturnStatus200(){

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("/rest/friend/{id}")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("/rest/friend/{id}")
                .then()
                .statusCode(404);
    }
}

package com.autentia.sharedjpa.restController.integrationTest.flywayContainer;

import com.autentia.sharedjpa.primaryAdapter.request.ExpenseRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class RestExpenseControllerITflyway {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("simple_shared")
            .withUsername("root")
            .withPassword("root")
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

    @Test
    @Order(1)
    public void shouldAddNewExpenseAndReturnStatus200(){
        long id = 1;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(new ExpenseRequest(10.00, "test"))
                .when()
                .post("/rest/expense/friend/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void shouldGetAllExpensesAndReturnStatus200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/rest/expense")
                .then()
                .statusCode(200)
                .body(".", hasSize(10));
    }

    @Test
    @Order(3)
    public void shouldReturnFriendNotFoundExceptionWhenAddingNewExpenseForFriend() {
        ExpenseRequest expenseRequest = new ExpenseRequest(10.00, "test");
        long id = 99;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(expenseRequest)
                .when()
                .post("/rest/expense/friend/{id}")
                .then()
                .statusCode(404);

    }

    @Test
    @Order(4)
    public void shouldReturnNegativeExpenseAmountExceptionWhenAmountIsNegative() {
        long id = 1;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(new ExpenseRequest(-10.00, "test"))
                .when()
                .post("/rest/expense/friend/{id}")
                .then()
                .statusCode(400);
    }
}

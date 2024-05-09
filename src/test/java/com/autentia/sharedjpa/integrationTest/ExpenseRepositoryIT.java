package com.autentia.sharedjpa.integrationTest;

import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.exception.NegativeExpenseAmountException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.ExpenseRepository;
import com.autentia.sharedjpa.secondaryAdapter.entity.ExpenseEntity;
import io.restassured.RestAssured;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class ExpenseRepositoryIT {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRepositoryIT(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

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
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
    public void shouldFindAllExpenses() throws EmptyExpenseListException, FriendNotFoundException {
        // WHEN
        List<Expense> expenses = expenseRepository.findExpenses();

        // THEN
        assertThat(expenses.size()).isEqualTo(9);
    }

    @Test
    @Order(2)
    public void shouldSaveNewExpense() throws NegativeExpenseAmountException, EmptyExpenseListException, FriendNotFoundException {
        // GIVEN
        long id = 8000;
        Friend friend = new Friend(3, "Pedro");
        Expense expected = new Expense(id, friend, 20.00, "Test", "2024-04-10 12:30:00");

        // WHEN
        expenseRepository.addExpense(expected);
        List<Expense> allExpenses = expenseRepository.findExpenses();

        // THEN
        assertTrue(allExpenses.contains(expected));
    }

    @Test
    @Order(3)
    public void shouldFindExpensesOfFriend() {
        // GIVEN
        long friendId = 2;

        // WHEN
        List<ExpenseEntity> expensesOfFriend = expenseRepository.findExpensesOfFriend(friendId);

        // THEN
        assertThat(expensesOfFriend.size()).isEqualTo(3);
    }

    @Test
    @Order(4)
    public void shouldGetAverageSpending() throws EmptyExpenseListException {
        // WHEN
        double averageSpending = expenseRepository.getAverageSpending();

        // THEN
        assertThat(averageSpending).isEqualTo(35.56, within(0.01));
    }
}

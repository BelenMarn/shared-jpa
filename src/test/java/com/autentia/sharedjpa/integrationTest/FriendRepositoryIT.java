package com.autentia.sharedjpa.integrationTest;

import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
import io.restassured.RestAssured;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class FriendRepositoryIT {

    private final FriendRepository friendRepository;
    private final long id = 90;
    private final String name = "Test";

    @Autowired
    public FriendRepositoryIT(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
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

    @BeforeEach
    void beforeEach() {
        // GIVEN
        Friend expected = new Friend(id, name);

        // WHEN
        friendRepository.save(expected);
        Friend response = friendRepository.findFriendById(id);

        // THEN
        assertThat(response).isEqualTo(expected);

    }

    @Test
    @Order(1)
    public void shouldFindAllFriends() throws EmptyFriendListException {
        // WHEN
        List<Friend> friends = friendRepository.findAll();

        // THEN
        assertThat(friends).isNotEmpty();
    }


    @Test
    @Order(2)
    public void shouldFindFriendById() {
        Friend expected = new Friend(id, name);

        // WHEN
        Friend response = friendRepository.findFriendById(id);

        // THEN
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @Order(3)
    public void shouldUpdateFriend() {
    // GIVEN
        Friend expected = new Friend(id, "Leo");

        // WHEN
        friendRepository.update(id, "Leo");
        Friend response = friendRepository.findFriendById(id);

        // THEN
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @Order(4)
    public void shouldGetNumberOfFriends() throws EmptyExpenseListException {
        // WHEN
        int numberOfFriends = friendRepository.getNumberOfFriends();

        // THEN
        assertThat(numberOfFriends).isGreaterThan(0);
    }

    @Test
    @Order(5)
    public void shouldGetBalanceOfFriend() {
        // GIVEN
        long friendId = 2;
        double expectedBalance = 70.0;

        // WHEN
        double balance = friendRepository.getBalanceOfFriend(friendId);

        // THEN
        assertThat(balance).isEqualTo(expectedBalance);
    }


    @Test
    @Order(6)
    public void shouldDeleteFriend() throws EmptyFriendListException {
        // WHEN

        // THEN
        friendRepository.deleteFriend(id);
        List<Friend> friends = friendRepository.findAll();

        // THEN
        assertThat(friends).isNotEmpty();

    }

}

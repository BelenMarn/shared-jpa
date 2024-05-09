package com.autentia.sharedjpa.integrationTest;

import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestPropertySource("/application-test.properties")
public class FriendRepositoryIT {

    private final FriendRepository friendRepository;

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
        flyway.migrate();
    }

    @Test
    @Order(1)
    public void shouldFindAllFriends() throws EmptyFriendListException {
        // WHEN
        List<Friend> friends = friendRepository.findAll();

        // THEN
        assertThat(friends).hasSize(4);
    }

    @Test
    @Order(2)
    public void shouldFindFriendById() throws FriendNotFoundException {
        long idFriend = 4;
        Friend expected = new Friend(idFriend, "Ismael");

        // WHEN
        Friend response = friendRepository.findFriendById(idFriend);

        // THEN
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @Order(3)
    public void shouldSaveNewFriend() throws FriendNotFoundException {
        // GIVEN
        long id = 005;
        Friend expected = new Friend(id,"Friend to Test");

        // WHEN
        friendRepository.save(expected);
        Friend response = friendRepository.findFriendById(id);

        // THEN
        assertThat(response).isEqualTo(expected);

    }

    @Test
    @Order(4)
    public void shouldUpdateFriend() throws FriendNotFoundException {
        // GIVEN
        long id = 1;
        String name = "Update";

        Friend expected = new Friend(id, name);

        // WHEN
        friendRepository.update(id, name);
        Friend response = friendRepository.findFriendById(id);

        // THEN
        assertThat(response).isEqualTo(expected);
    }

    @Test
    @Order(5)
    public void shouldGetNumberOfFriends() throws EmptyExpenseListException {
        // WHEN
        int numberOfFriends = friendRepository.getNumberOfFriends();

        // THEN
        assertThat(numberOfFriends).isEqualTo(4);
    }

    @Test
    @Order(6)
    public void shouldGetBalanceOfFriend() {
        // GIVEN
        long friendId = 2;
        double expectedBalance = 70.0;

        // WHEN
        double balance = friendRepository.getBalanceOfFriend(friendId);

        // THEN
        assertThat(balance).isEqualTo(expectedBalance);
    }
}

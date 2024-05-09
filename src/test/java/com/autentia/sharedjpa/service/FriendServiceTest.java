package com.autentia.sharedjpa.service;


import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
import com.autentia.sharedjpa.core.service.FriendService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class FriendServiceTest {
    private final FriendRepository repository = Mockito.mock(FriendRepository.class);
    private final FriendService sut = new FriendService(repository);

    @Test
    public void shouldFindAllFriends() throws EmptyFriendListException {
        //GIVEN
        final List<Friend> expected = new ArrayList<>();
        expected.add(new Friend(1, "Juan"));
        expected.add(new Friend(2, "Maria"));
        Mockito.when(repository.findAll()).thenReturn(expected);

        //WHEN
        final List<Friend> result = sut.findFriends();

        //THEN
        Assertions.assertEquals(expected.size(), result.size());
        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    public void shoulThrowExceptionWhenEmpyFriends() throws EmptyFriendListException {
        //GIVEN
        final List<Friend> expected = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(expected);

        //WHEN
        if(expected.isEmpty()){
            EmptyFriendListException exception = Assertions.assertThrows(EmptyFriendListException.class, () -> sut.findFriends());

            //THEN
            Assertions.assertEquals("Empty friend list", exception.getMessage());
        }

    }

    @Test
    public void shouldFindFriendById() throws EmptyFriendListException, FriendNotFoundException {
        //GIVEN:
        final Friend expected = new Friend(1, "Juan");
        when(repository.findFriendById(1)).thenReturn(expected);

        //WHEN:
        final Friend result = sut.findFriendById(1);

        //THEN:
        Assertions.assertEquals(expected, result);

    }

    @Test
    public void shouldThrowExceptionWhenFriendNotFound() throws FriendNotFoundException {
        //GIVEN:
        when(repository.findFriendById(1)).thenReturn(null);

        //WHEN:
        FriendNotFoundException exception = Assertions.assertThrows(FriendNotFoundException.class, () -> sut.findFriendById(1));

        //THEN:
        Assertions.assertEquals("Friend not found", exception.getMessage());
    }

    @Test
    public void shouldInsertFriend() {
        //GIVEN:
        final Friend request = new Friend(
                1,
                "Paca"
        );

        //WHEN:
        sut.addFriend(request);
        //THEN:
        verify(repository, times(1)).save(request);
    }

    @Test
    public void shouldUpdateFriend() {
        //GIVEN:
        long id =1;
        String name = "Paco";

        //WHEN:
        sut.updateFriend(id, name);
        //THEN:
        Mockito.verify(repository, Mockito.times(1)).update(id, name);
    }
}

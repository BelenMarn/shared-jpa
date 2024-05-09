package com.autentia.sharedjpa.restController.unitTest;


import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.service.FriendService;
import com.autentia.sharedjpa.primaryAdapter.request.FriendRequest;
import com.autentia.sharedjpa.primaryAdapter.respond.FriendResponse;
import com.autentia.sharedjpa.primaryAdapter.restController.RestFriendController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RestFriendControllerTest {

    private final FriendService service = Mockito.mock(FriendService.class);
    private final RestFriendController sut = new RestFriendController(service);

    @Test
    public void shouldGetAllFriends() throws EmptyFriendListException {
        //GIVEN:
        final List<Friend> training = new ArrayList<>();
        training.add(new Friend(1, "Juan"));
        training.add(new Friend(2, "Maria"));
        when(service.findFriends()).thenReturn(training);

        final List<FriendResponse> expected = new ArrayList<>();
        expected.add(new FriendResponse(1, "Juan"));
        expected.add(new FriendResponse(2, "Maria"));

        //WHEN
        List<FriendResponse> result = sut.findAll();

        //THEN
        assertEquals(expected.size(), result.size());
        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    public void shoulThrowExceptionWhenEmpyFriends() throws EmptyFriendListException {
        // GIVEN
        List<Friend> expected = new ArrayList<>();
        when(service.findFriends()).thenReturn(expected);

        // WHEN
        if (expected.isEmpty()) {
            EmptyFriendListException exception = assertThrows(EmptyFriendListException.class, () -> sut.findAll());

            // THEN
            assertEquals("Empty friend list", exception.getMessage());
        }
    }

    @Test
    public void shouldFindFriendById() throws EmptyFriendListException, FriendNotFoundException {
        //GIVEN:
        final Friend expected = new Friend(1, "Juan");
        when(service.findFriendById(1)).thenReturn(expected);

        //WHEN:
        final Friend result = sut.findById(1);
        //THEN:
        assertEquals(expected, result);
    }

    @Test
    public void shouldThrowExceptionWhenFriendNotFound() throws EmptyFriendListException, FriendNotFoundException {
        //GIVEN:
        final Friend expected = new Friend(1, "Juan");
        when(service.findFriendById(1)).thenReturn(null);

        //WHEN:
        FriendNotFoundException exception = Assertions.assertThrows(FriendNotFoundException.class, () -> sut.findById(1));
        //THEN:
        Assertions.assertEquals("Friend not found", exception.getMessage());
    }

    @Test
    public void shouldAddaFriend(){
        //GIVEN:
        final FriendRequest request = new FriendRequest(
            1,
            "Paca"
        );

        final Friend expected = new Friend(
            1,
            "Paca"
        );

        //WHEN:
        sut.newFriend(request);
        //THEN:
        verify(service, times(1)).addFriend(expected);
    }

    @Test
    public void shouldUpdateFriend(){
        //GIVEN:
        long id =1;
        String name = "Paco";

        //WHEN:
        sut.updateFriend(id, name);
        //THEN:
        Mockito.verify(service, Mockito.times(1)).updateFriend(id, name);

    }
}
package com.autentia.sharedjpa.restController.unitTest;


import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.exception.NegativeExpenseAmountException;
import com.autentia.sharedjpa.core.service.ExpenseService;
import com.autentia.sharedjpa.primaryAdapter.request.ExpenseRequest;
import com.autentia.sharedjpa.primaryAdapter.respond.ExpenseResponse;
import com.autentia.sharedjpa.primaryAdapter.restController.RestExpenseController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RestExpenseControllerTest {
    private final ExpenseService service = mock(ExpenseService.class);
    private final RestExpenseController sut = new RestExpenseController(service);

    @Test
    public void shouldReturnAllExpenses() throws EmptyExpenseListException, FriendNotFoundException {
        //GIVEN
        List<Expense> training = new ArrayList<Expense>();
        training.add(new Expense(1, new Friend(1, "Paco"), 50.00, "Train", "2024-04-10 12:30:00"));
        when(service.findExpenses()).thenReturn(training);

        List<ExpenseResponse> expected = new ArrayList<>();
        expected.add(new ExpenseResponse(1, 1, "Paco", 50.00, "Train", "2024-04-10 12:30:00"));

        //WHEN
        List<ExpenseResponse> result = sut.findExpenses();

        //THEN
        assertEquals(expected.size(), result.size());
        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    public void shouldAddExpense() throws NegativeExpenseAmountException, EmptyFriendListException, FriendNotFoundException {
        //GIVEN:
        ExpenseRequest result = new ExpenseRequest(50.00, "Train");
        ExpenseRequest expected = new ExpenseRequest(50.00, "Train");

        //WHEN:
        sut.addExpense(1, result);
        //THEN:
        Mockito.verify(service, times(1)).addExpense(1, expected);
    }

}

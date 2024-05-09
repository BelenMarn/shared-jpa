package com.autentia.sharedjpa.core.domain.exception;

public class EmptyExpenseListException extends Exception{
    public EmptyExpenseListException(String message) {
        super(message);
    }
}

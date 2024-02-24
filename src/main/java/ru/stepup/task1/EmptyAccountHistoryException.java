package ru.stepup.task1;

public class EmptyAccountHistoryException extends RuntimeException{
    public EmptyAccountHistoryException() {
        super();
    }
    public EmptyAccountHistoryException(String message) {
        super(message);
    }
}

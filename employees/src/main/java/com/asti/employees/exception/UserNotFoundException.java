package com.asti.employees.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id) {
        super("User with ID " + id + " not found");
    }

    public UserNotFoundException(final String username) {
        super("User with USERNAME " + username + " not found");
    }
}

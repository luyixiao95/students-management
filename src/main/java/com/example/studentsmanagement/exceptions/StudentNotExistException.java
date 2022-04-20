package com.example.studentsmanagement.exceptions;

public class StudentNotExistException extends RuntimeException{
    public StudentNotExistException(String message) {
        super(message);
    }
}

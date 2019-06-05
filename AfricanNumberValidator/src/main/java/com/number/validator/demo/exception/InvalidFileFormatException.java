package com.number.validator.demo.exception;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException()
    {
        super("File format is invalid");
    }
}

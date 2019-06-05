package com.number.validator.demo.exception;

public class NotAcceptableFileExtension extends Exception {
    public NotAcceptableFileExtension()
    {
        super("File extension is invalid");
    }
}
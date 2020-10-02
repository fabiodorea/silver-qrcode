package com.sinqia.silver.exception;

public class CharacterLimitExceededException extends RuntimeException {

    public CharacterLimitExceededException(String s) {
        super(s);
    }
}

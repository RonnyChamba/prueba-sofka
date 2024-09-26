package com.assessment.sofka.mscorepersona.exeption;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String userMessage;

    public GenericException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;
    }
}

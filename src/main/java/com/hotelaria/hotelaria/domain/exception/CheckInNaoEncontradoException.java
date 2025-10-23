package com.hotelaria.hotelaria.domain.exception;

public class CheckInNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public CheckInNaoEncontradoException(String message) {
        super(message);
    }

    public CheckInNaoEncontradoException(Long checkInId) {
        this(String.format("Não existe um cadastro de check-in com código %d", checkInId));
    }
}

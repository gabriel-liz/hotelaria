package com.hotelaria.hotelaria.domain.exception;

public class HospedeNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public HospedeNaoEncontradoException(String message) {
        super(message);
    }

    public HospedeNaoEncontradoException(Long hospedeId) {
        this(String.format("Não existe cadastro de hóspede com código %d", hospedeId));
    }
}

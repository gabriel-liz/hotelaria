package com.hotelaria.hotelaria.domain.exception;

public class HospedeNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public HospedeNaoEncontradoException(String message) {
        super(message);
    }

    public HospedeNaoEncontradoException(Long hospedeId) {
        this(String.format("Não existe um cadastro de hospede com código %d", hospedeId));
    }
}

package com.hotelaria.hotelaria.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    CORPO_INVALIDO("/corpo-invalido", "O Corpo informado no json é inválido"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

    private String tittle;
    private String uri;

    private ProblemType(String path, String tittle) {
        this.uri = "localhost:8080" + path;
        this.tittle = tittle;
    }

}

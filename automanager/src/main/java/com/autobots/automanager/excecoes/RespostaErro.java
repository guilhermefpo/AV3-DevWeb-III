package com.autobots.automanager.excecoes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RespostaErro {
    private Integer status;
    private String mensagem;
    private String statusCode;
}

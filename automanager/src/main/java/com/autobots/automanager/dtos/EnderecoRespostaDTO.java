package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class EnderecoRespostaDTO extends RepresentationModel<EnderecoRespostaDTO> {

    private Long id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String codigoPostal;
    private String informacoesAdicionais;
}

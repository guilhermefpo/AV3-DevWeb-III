package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class EmpresaRespostaDTO extends RepresentationModel<EmpresaRespostaDTO> {
    private Long id;
    private String nome;
    private String cnpj;
}

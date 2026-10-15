package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TelefoneRespostaDTO extends RepresentationModel<TelefoneRespostaDTO> {
    private Long id;
    private String ddd;
    private String numero;
}

package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class EmailRespostaDTO extends RepresentationModel<EmailRespostaDTO> {
    private Long id;
    private String endereco;
}

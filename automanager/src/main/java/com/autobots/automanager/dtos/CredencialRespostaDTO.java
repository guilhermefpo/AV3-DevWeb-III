package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CredencialRespostaDTO extends RepresentationModel<CredencialRespostaDTO> {
    private Long id;
    private String login;
}

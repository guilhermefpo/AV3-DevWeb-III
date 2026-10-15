package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class DocumentoRespostaDTO extends RepresentationModel<DocumentoRespostaDTO> {
    private Long id;
    private String tipo;
    private String numero;
}

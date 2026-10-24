package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import com.autobots.automanager.enumeracao.TipoVeiculo;

@Data
public class VeiculoRespostaDTO extends RepresentationModel<VeiculoRespostaDTO> {
    private Long id;
    private TipoVeiculo tipo;
    private String marca;
    private String modelo;
    private String placa;
    private Integer ano;
    private Long usuarioId;
}

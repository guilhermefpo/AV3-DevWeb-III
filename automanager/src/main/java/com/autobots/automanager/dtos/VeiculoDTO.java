package com.autobots.automanager.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import com.autobots.automanager.enumeracao.TipoVeiculo;

@Data
public class VeiculoDTO {
    @NotNull(message = "Tipo do veículo é obrigatório")
    private TipoVeiculo tipo;
    private String marca;
    private String modelo;
    @NotBlank(message = "Placa é obrigatória")
    private String placa;
    private Integer ano;
}

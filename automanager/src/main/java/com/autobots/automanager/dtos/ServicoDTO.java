package com.autobots.automanager.dtos;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ServicoDTO {
    @NotBlank(message = "Nome do serviço é obrigatório")
    private String nome;
    private String descricao;
    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal preco;
}

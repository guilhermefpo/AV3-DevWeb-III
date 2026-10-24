package com.autobots.automanager.dtos;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MercadoriaDTO {
    @NotBlank(message = "Nome da mercadoria é obrigatório")
    private String nome;
    private String descricao;
    @PositiveOrZero(message = "Estoque não pode ser negativo")
    private Integer estoque;
    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal preco;
}

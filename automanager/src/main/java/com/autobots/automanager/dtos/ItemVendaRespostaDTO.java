package com.autobots.automanager.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemVendaRespostaDTO {
    private Long mercadoriaId;
    private String mercadoriaNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
}

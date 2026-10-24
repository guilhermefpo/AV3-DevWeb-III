package com.autobots.automanager.dtos;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendaDTO {
    @NotNull(message = "Empresa é obrigatória")
    private Long empresaId;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    private Long veiculoId;

    @NotEmpty(message = "A venda deve conter ao menos um item")
    @Valid
    private List<ItemVendaDTO> itens;
}

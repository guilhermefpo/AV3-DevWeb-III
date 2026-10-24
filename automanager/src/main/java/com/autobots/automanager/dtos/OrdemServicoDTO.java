package com.autobots.automanager.dtos;

import javax.validation.constraints.NotNull;
import com.autobots.automanager.enumeracao.StatusOrdemServico;
import lombok.Data;

@Data
public class OrdemServicoDTO {
    @NotNull(message = "Empresa é obrigatória")
    private Long empresaId;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "Veículo é obrigatório")
    private Long veiculoId;

    @NotNull(message = "Serviço é obrigatório")
    private Long servicoId;

    private StatusOrdemServico status;
    private String observacao;
}

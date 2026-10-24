package com.autobots.automanager.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.autobots.automanager.enumeracao.StatusOrdemServico;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class OrdemServicoRespostaDTO extends RepresentationModel<OrdemServicoRespostaDTO> {
    private Long id;
    private Long empresaId;
    private Long usuarioId;
    private Long veiculoId;
    private String placaVeiculo;
    private Long servicoId;
    private String nomeServico;
    private BigDecimal valorCobrado;
    private String observacao;
    private LocalDateTime dataCriacao;
    private StatusOrdemServico status;
}

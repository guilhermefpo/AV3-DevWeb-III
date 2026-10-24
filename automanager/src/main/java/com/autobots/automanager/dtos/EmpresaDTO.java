package com.autobots.automanager.dtos;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaDTO {
    @NotBlank(message = "Nome da empresa é obrigatório")
    private String nome;
    private String cnpj;
}

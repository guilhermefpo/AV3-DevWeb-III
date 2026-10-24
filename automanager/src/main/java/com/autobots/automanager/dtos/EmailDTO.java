package com.autobots.automanager.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDTO {
    @NotBlank(message = "Endereço de e-mail é obrigatório")
    @Email(message = "Endereço de e-mail inválido")
    private String endereco;
}

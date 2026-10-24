package com.autobots.automanager.dtos;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredencialDTO {
    @NotBlank(message = "Login é obrigatório")
    private String login;
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}

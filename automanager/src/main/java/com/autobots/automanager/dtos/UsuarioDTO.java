package com.autobots.automanager.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import com.autobots.automanager.enumeracao.PerfilUsuario;

@Data
public class UsuarioDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private String documento;
    @Valid
    private EmailDTO email;
    @NotNull(message = "Perfil é obrigatório")
    private PerfilUsuario perfil;
    @Valid
    private CredencialDTO credencial;
}

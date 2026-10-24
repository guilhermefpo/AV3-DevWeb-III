package com.autobots.automanager.dtos;

import java.util.List;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import com.autobots.automanager.enumeracao.PerfilUsuario;

@Data
public class UsuarioRespostaDTO extends RepresentationModel<UsuarioRespostaDTO> {
    private Long id;
    private String nome;
    private String documento;
    private EmailRespostaDTO email;
    private PerfilUsuario perfil;
    private CredencialRespostaDTO credencial;
    private Long empresaId;
    private List<VeiculoRespostaDTO> veiculos;
}

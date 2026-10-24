package com.autobots.automanager.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.dtos.OrdemServicoDTO;
import com.autobots.automanager.dtos.OrdemServicoRespostaDTO;
import com.autobots.automanager.servicos.OrdemServicoServicos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoControle {
    private final OrdemServicoServicos servicos;

    public OrdemServicoControle(OrdemServicoServicos servicos) {
        this.servicos = servicos;
    }

    @GetMapping
    public List<OrdemServicoRespostaDTO> listar() {
        return servicos.listar().stream().map(this::links).toList();
    }

    @GetMapping("/{id}")
    public OrdemServicoRespostaDTO buscar(@PathVariable Long id) {
        return links(servicos.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoRespostaDTO cadastrar(@Valid @RequestBody OrdemServicoDTO dto) {
        return links(servicos.salvar(null, dto));
    }

    @PutMapping("/{id}")
    public OrdemServicoRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody OrdemServicoDTO dto) {
        return links(servicos.salvar(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        servicos.excluir(id);
    }

    private OrdemServicoRespostaDTO links(OrdemServicoRespostaDTO dto) {
        dto.add(linkTo(methodOn(OrdemServicoControle.class).buscar(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(OrdemServicoControle.class).listar()).withRel("ordens-servico"));
        return dto;
    }
}

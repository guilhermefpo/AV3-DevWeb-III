package com.autobots.automanager.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.dtos.ServicoDTO;
import com.autobots.automanager.dtos.ServicoRespostaDTO;
import com.autobots.automanager.servicos.ServicoServicos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/servico")
public class ServicoControle {
    private final ServicoServicos s;

    public ServicoControle(ServicoServicos s) {
        this.s = s;
    }

    @GetMapping
    public List<ServicoRespostaDTO> listar() {
        return s.listar().stream().map(this::links).toList();
    }

    @GetMapping("/{id}")
    public ServicoRespostaDTO buscar(@PathVariable Long id) {
        return links(s.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoRespostaDTO cadastrar(@Valid @RequestBody ServicoDTO e, @RequestParam Long empresaId) {
        return links(s.salvar(null, e, empresaId));
    }

    @PutMapping("/{id}")
    public ServicoRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody ServicoDTO e,
            @RequestParam(required = false) Long empresaId) {
        return links(s.salvar(id, e, empresaId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        s.excluir(id);
    }
    private ServicoRespostaDTO links(ServicoRespostaDTO e) { e.add(linkTo(methodOn(ServicoControle.class).buscar(e.getId())).withSelfRel()); e.add(linkTo(methodOn(ServicoControle.class).listar()).withRel("servicos")); return e; }
}

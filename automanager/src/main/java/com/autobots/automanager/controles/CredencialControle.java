package com.autobots.automanager.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.dtos.CredencialDTO;
import com.autobots.automanager.dtos.CredencialRespostaDTO;
import com.autobots.automanager.servicos.CredencialServicos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {
    private final CredencialServicos s;

    public CredencialControle(CredencialServicos s) {
        this.s = s;
    }

    @GetMapping
    public List<CredencialRespostaDTO> listar() {
        return s.listar().stream().map(this::links).toList();
    }

    @GetMapping("/{id}")
    public CredencialRespostaDTO buscar(@PathVariable Long id) {
        return links(s.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CredencialRespostaDTO cadastrar(@Valid @RequestBody CredencialDTO e) {
        return links(s.salvar(null, e));
    }

    @PutMapping("/{id}")
    public CredencialRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody CredencialDTO e) {
        return links(s.salvar(id, e));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        s.excluir(id);
    }
    private CredencialRespostaDTO links(CredencialRespostaDTO e) { e.add(linkTo(methodOn(CredencialControle.class).buscar(e.getId())).withSelfRel()); e.add(linkTo(methodOn(CredencialControle.class).listar()).withRel("credenciais")); return e; }
}

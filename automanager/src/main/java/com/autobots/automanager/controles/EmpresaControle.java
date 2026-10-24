package com.autobots.automanager.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.dtos.EmpresaDTO;
import com.autobots.automanager.dtos.EmpresaRespostaDTO;
import com.autobots.automanager.servicos.EmpresaServicos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
    private final EmpresaServicos s;

    public EmpresaControle(EmpresaServicos s) {
        this.s = s;
    }

    @GetMapping
    public List<EmpresaRespostaDTO> listar() {
        return s.listar().stream().map(this::links).toList();
    }

    @GetMapping("/{id}")
    public EmpresaRespostaDTO buscar(@PathVariable Long id) {
        return links(s.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaRespostaDTO cadastrar(@Valid @RequestBody EmpresaDTO e) {
        return links(s.salvar(null, e));
    }

    @PutMapping("/{id}")
    public EmpresaRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody EmpresaDTO e) {
        return links(s.salvar(id, e));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        s.excluir(id);
    }
    private EmpresaRespostaDTO links(EmpresaRespostaDTO e) { e.add(linkTo(methodOn(EmpresaControle.class).buscar(e.getId())).withSelfRel()); e.add(linkTo(methodOn(EmpresaControle.class).listar()).withRel("empresas")); return e; }
}

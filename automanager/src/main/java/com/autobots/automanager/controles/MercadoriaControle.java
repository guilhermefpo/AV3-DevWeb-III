package com.autobots.automanager.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.dtos.MercadoriaDTO;
import com.autobots.automanager.dtos.MercadoriaRespostaDTO;
import com.autobots.automanager.servicos.MercadoriaServicos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
    private final MercadoriaServicos s;

    public MercadoriaControle(MercadoriaServicos s) {
        this.s = s;
    }

    @GetMapping
    public List<MercadoriaRespostaDTO> listar() {
        return s.listar().stream().map(this::links).toList();
    }

    @GetMapping("/{id}")
    public MercadoriaRespostaDTO buscar(@PathVariable Long id) {
        return links(s.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MercadoriaRespostaDTO cadastrar(@Valid @RequestBody MercadoriaDTO e, @RequestParam Long empresaId) {
        return links(s.salvar(null, e, empresaId));
    }

    @PutMapping("/{id}")
    public MercadoriaRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody MercadoriaDTO e,
            @RequestParam(required = false) Long empresaId) {
        return links(s.salvar(id, e, empresaId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        s.excluir(id);
    }
    private MercadoriaRespostaDTO links(MercadoriaRespostaDTO e) { e.add(linkTo(methodOn(MercadoriaControle.class).buscar(e.getId())).withSelfRel()); e.add(linkTo(methodOn(MercadoriaControle.class).listar()).withRel("mercadorias")); return e; }
}

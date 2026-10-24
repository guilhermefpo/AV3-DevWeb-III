package com.autobots.automanager.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.dtos.EmailDTO;
import com.autobots.automanager.dtos.EmailRespostaDTO;
import com.autobots.automanager.servicos.EmailServicos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/email")
public class EmailControle {
    private final EmailServicos servicos;

    public EmailControle(EmailServicos servicos) {
        this.servicos = servicos;
    }

    @GetMapping
    public List<EmailRespostaDTO> listar() {
        return servicos.listar().stream().map(this::links).toList();
    }

    @GetMapping("/{id}")
    public EmailRespostaDTO buscar(@PathVariable Long id) {
        return links(servicos.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmailRespostaDTO cadastrar(@Valid @RequestBody EmailDTO email) {
        return links(servicos.salvar(null, email));
    }

    @PutMapping("/{id}")
    public EmailRespostaDTO atualizar(@PathVariable Long id, @Valid @RequestBody EmailDTO email) {
        return links(servicos.salvar(id, email));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        servicos.excluir(id);
    }

    private EmailRespostaDTO links(EmailRespostaDTO email) {
        email.add(linkTo(methodOn(EmailControle.class).buscar(email.getId())).withSelfRel());
        email.add(linkTo(methodOn(EmailControle.class).listar()).withRel("emails"));
        return email;
    }
}

package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import javax.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.repositorios.EmailRepositorio;
import com.autobots.automanager.dtos.EmailDTO;
import com.autobots.automanager.dtos.EmailRespostaDTO;

@Service
public class EmailServicos {
    private final EmailRepositorio repositorio;
    private final ModelMapper mapper;

    public EmailServicos(EmailRepositorio repositorio, ModelMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    public List<EmailRespostaDTO> listar() {
        return repositorio.findAll().stream().map(e -> mapper.map(e, EmailRespostaDTO.class)).collect(Collectors.toList());
    }

    public EmailRespostaDTO buscar(Long id) {
        return mapper.map(repositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("E-mail não encontrado: " + id)), EmailRespostaDTO.class);
    }

    public EmailRespostaDTO salvar(Long id, EmailDTO dto) {
        Email email = id == null ? new Email() : repositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("E-mail não encontrado: " + id));
        mapper.map(dto, email);
        if (email.getEndereco() != null && (email.getId() == null
                ? repositorio.existsByEndereco(email.getEndereco())
                : repositorio.existsByEnderecoAndIdNot(email.getEndereco(), email.getId()))) {
            throw new DataIntegrityViolationException("E-mail já cadastrado.");
        }
        return mapper.map(repositorio.save(email), EmailRespostaDTO.class);
    }

    public void excluir(Long id) {
        repositorio.delete(repositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("E-mail não encontrado: " + id)));
    }
}

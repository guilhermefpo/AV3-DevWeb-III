package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import javax.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import com.autobots.automanager.dtos.CredencialDTO;
import com.autobots.automanager.dtos.CredencialRespostaDTO;

@Service
public class CredencialServicos {
    private final CredencialRepositorio r;
    private final ModelMapper mapper;

    public CredencialServicos(CredencialRepositorio r, ModelMapper mapper) {
        this.r = r;
        this.mapper = mapper;
    }

    public List<CredencialRespostaDTO> listar() {
        return r.findAll().stream().map(e -> mapper.map(e, CredencialRespostaDTO.class)).collect(Collectors.toList());
    }

    public CredencialRespostaDTO buscar(Long id) {
        return mapper.map(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Credencial não encontrada: " + id)), CredencialRespostaDTO.class);
    }

    public CredencialRespostaDTO salvar(Long id, CredencialDTO dto) {
        Credencial e = id == null ? new Credencial() : r.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Credencial não encontrada: " + id));
        mapper.map(dto, e);
        if (e.getLogin() != null && (e.getId() == null ? r.existsByLogin(e.getLogin())
                : r.existsByLoginAndIdNot(e.getLogin(), e.getId()))) {
            throw new DataIntegrityViolationException("Login já cadastrado.");
        }
        return mapper.map(r.save(e), CredencialRespostaDTO.class);
    }

    public void excluir(Long id) {
        r.delete(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Credencial não encontrada: " + id)));
    }
}

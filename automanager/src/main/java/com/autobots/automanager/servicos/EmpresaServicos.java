package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.dtos.EmpresaDTO;
import com.autobots.automanager.dtos.EmpresaRespostaDTO;
import javax.persistence.EntityNotFoundException;

@Service
public class EmpresaServicos {
    private final EmpresaRepositorio r;
    private final ModelMapper mapper;

    public EmpresaServicos(EmpresaRepositorio r, ModelMapper mapper) {
        this.r = r;
        this.mapper = mapper;
    }

    public List<EmpresaRespostaDTO> listar() {
        return r.findAll().stream().map(e -> mapper.map(e, EmpresaRespostaDTO.class)).collect(Collectors.toList());
    }

    public EmpresaRespostaDTO buscar(Long id) {
        return mapper.map(
                r.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + id)),
                EmpresaRespostaDTO.class);
    }

    public EmpresaRespostaDTO salvar(Long id, EmpresaDTO dto) {
        Empresa e = id == null ? new Empresa()
                : r.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + id));
        mapper.map(dto, e);
        return mapper.map(r.save(e), EmpresaRespostaDTO.class);
    }

    public void excluir(Long id) {
        r.delete(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + id)));
    }
}

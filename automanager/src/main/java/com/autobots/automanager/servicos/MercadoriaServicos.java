package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import javax.persistence.EntityNotFoundException;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.dtos.MercadoriaDTO;
import com.autobots.automanager.dtos.MercadoriaRespostaDTO;

@Service
public class MercadoriaServicos {
    private final MercadoriaRepositorio r;
    private final EmpresaRepositorio empresas;
    private final ModelMapper mapper;

    public MercadoriaServicos(MercadoriaRepositorio r, EmpresaRepositorio empresas, ModelMapper mapper) {
        this.r = r;
        this.empresas = empresas;
        this.mapper = mapper;
    }

    public List<MercadoriaRespostaDTO> listar() {
        return r.findAll().stream().map(e -> mapper.map(e, MercadoriaRespostaDTO.class)).collect(Collectors.toList());
    }

    public MercadoriaRespostaDTO buscar(Long id) {
        return mapper.map(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercadoria não encontrada: " + id)), MercadoriaRespostaDTO.class);
    }

    public MercadoriaRespostaDTO salvar(Long id, MercadoriaDTO dto, Long empresaId) {
        Mercadoria e = id == null ? new Mercadoria() : r.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mercadoria não encontrada: " + id));
        mapper.map(dto, e);
        if (empresaId != null) {
            Empresa empresa = empresas.findById(empresaId)
                    .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + empresaId));
            e.setEmpresa(empresa);
        } else if (id == null) {
            throw new IllegalArgumentException("Informe empresaId para associar a mercadoria.");
        }
        return mapper.map(r.save(e), MercadoriaRespostaDTO.class);
    }

    public void excluir(Long id) {
        r.delete(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercadoria não encontrada: " + id)));
    }
}

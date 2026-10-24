package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import javax.persistence.EntityNotFoundException;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.dtos.ServicoDTO;
import com.autobots.automanager.dtos.ServicoRespostaDTO;

@Service
public class ServicoServicos {
    private final ServicoRepositorio r;
    private final EmpresaRepositorio empresas;
    private final ModelMapper mapper;

    public ServicoServicos(ServicoRepositorio r, EmpresaRepositorio empresas, ModelMapper mapper) {
        this.r = r;
        this.empresas = empresas;
        this.mapper = mapper;
    }

    public List<ServicoRespostaDTO> listar() {
        return r.findAll().stream().map(e -> mapper.map(e, ServicoRespostaDTO.class)).collect(Collectors.toList());
    }

    public ServicoRespostaDTO buscar(Long id) {
        return mapper.map(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado: " + id)), ServicoRespostaDTO.class);
    }

    public ServicoRespostaDTO salvar(Long id, ServicoDTO dto, Long empresaId) {
        Servico e = id == null ? new Servico() : r.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado: " + id));
        mapper.map(dto, e);
        if (empresaId != null) {
            Empresa empresa = empresas.findById(empresaId)
                    .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + empresaId));
            e.setEmpresa(empresa);
        } else if (id == null) {
            throw new IllegalArgumentException("Informe empresaId para associar o serviço.");
        }
        return mapper.map(r.save(e), ServicoRespostaDTO.class);
    }

    public void excluir(Long id) {
        r.delete(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado: " + id)));
    }
}

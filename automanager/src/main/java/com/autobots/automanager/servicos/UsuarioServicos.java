package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import javax.persistence.EntityNotFoundException;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.dtos.UsuarioDTO;
import com.autobots.automanager.dtos.UsuarioRespostaDTO;

@Service
@Transactional
public class UsuarioServicos {
    private final UsuarioRepositorio r;
    private final EmpresaRepositorio empresas;
    private final ModelMapper mapper;

    public UsuarioServicos(UsuarioRepositorio r, EmpresaRepositorio empresas, ModelMapper mapper) {
        this.r = r;
        this.empresas = empresas;
        this.mapper = mapper;
    }

    public List<UsuarioRespostaDTO> listar() {
        return r.findAll().stream().map(e -> mapper.map(e, UsuarioRespostaDTO.class)).collect(Collectors.toList());
    }

    public UsuarioRespostaDTO buscar(Long id) {
        return mapper.map(
                r.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id)),
                UsuarioRespostaDTO.class);
    }

    public UsuarioRespostaDTO salvar(Long id, UsuarioDTO dto, Long empresaId) {
        Usuario e = id == null ? new Usuario()
                : r.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
        mapper.map(dto, e);
        if (e.getDocumento() != null && (e.getId() == null ? r.existsByDocumento(e.getDocumento())
                : r.existsByDocumentoAndIdNot(e.getDocumento(), e.getId()))) {
            throw new org.springframework.dao.DataIntegrityViolationException("Documento já cadastrado.");
        }
        if (empresaId != null) {
            Empresa empresa = empresas.findById(empresaId)
                    .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + empresaId));
            e.setEmpresa(empresa);
        } else if (id == null) {
            e.setEmpresa(null);
        }
        return mapper.map(r.save(e), UsuarioRespostaDTO.class);
    }

    public void excluir(Long id) {
        r.delete(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id)));
    }
}

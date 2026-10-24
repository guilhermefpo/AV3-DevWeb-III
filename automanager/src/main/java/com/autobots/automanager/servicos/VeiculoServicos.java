package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import javax.persistence.EntityNotFoundException;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.dtos.VeiculoDTO;
import com.autobots.automanager.dtos.VeiculoRespostaDTO;

@Service
@Transactional
public class VeiculoServicos {
    private final VeiculoRepositorio r;
    private final UsuarioRepositorio usuarios;
    private final ModelMapper mapper;

    public VeiculoServicos(VeiculoRepositorio r, UsuarioRepositorio usuarios, ModelMapper mapper) {
        this.r = r;
        this.usuarios = usuarios;
        this.mapper = mapper;
    }

    public List<VeiculoRespostaDTO> listar() {
        return r.findAll().stream().map(e -> mapper.map(e, VeiculoRespostaDTO.class)).collect(Collectors.toList());
    }

    public VeiculoRespostaDTO buscar(Long id) {
        return mapper.map(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado: " + id)), VeiculoRespostaDTO.class);
    }

    public VeiculoRespostaDTO salvar(Long id, VeiculoDTO dto, Long usuarioId) {
        Veiculo e = id == null ? new Veiculo() : r.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado: " + id));
        mapper.map(dto, e);
        if (e.getPlaca() != null && (e.getId() == null ? r.existsByPlaca(e.getPlaca())
                : r.existsByPlacaAndIdNot(e.getPlaca(), e.getId()))) {
            throw new org.springframework.dao.DataIntegrityViolationException("Placa já cadastrada.");
        }
        if (usuarioId != null) {
            Usuario usuario = usuarios.findById(usuarioId)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + usuarioId));
            e.setUsuario(usuario);
        } else if (id == null) {
            throw new IllegalArgumentException("Informe usuarioId para associar o veículo.");
        }
        return mapper.map(r.save(e), VeiculoRespostaDTO.class);
    }

    public void excluir(Long id) {
        r.delete(r.findById(id).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado: " + id)));
    }
}

package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autobots.automanager.entidades.OrdemServico;

public interface OrdemServicoRepositorio extends JpaRepository<OrdemServico, Long> {
}

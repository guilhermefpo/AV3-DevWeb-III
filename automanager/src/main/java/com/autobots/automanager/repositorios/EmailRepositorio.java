package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autobots.automanager.entidades.Email;

public interface EmailRepositorio extends JpaRepository<Email, Long> {
    boolean existsByEndereco(String endereco);
    boolean existsByEnderecoAndIdNot(String endereco, Long id);
}

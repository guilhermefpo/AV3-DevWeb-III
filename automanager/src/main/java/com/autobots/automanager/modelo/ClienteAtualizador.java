package com.autobots.automanager.modelo;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.excecoes.CpfImutavelException;

@Component
public class ClienteAtualizador {
	private final StringVerificadorNulo verificador = new StringVerificadorNulo();
	private final EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private final DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private final TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

	private void atualizarDados(Cliente cliente, Cliente atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			cliente.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			cliente.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(atualizacao.getDataCadastro() == null)) {
			cliente.setDataCadastro(atualizacao.getDataCadastro());
		}
		if (!(atualizacao.getDataNascimento() == null)) {
			cliente.setDataNascimento(atualizacao.getDataNascimento());
		}
	}

	public void atualizar(Cliente cliente, Cliente atualizacao) {
		if (atualizacao.getCpf() != null
				&& !atualizacao.getCpf().equals(cliente.getCpf())) {
			throw new CpfImutavelException();
		}

		atualizarDados(cliente, atualizacao);

        if (atualizacao.getEndereco() != null) {
            if (cliente.getEndereco() == null) {
                cliente.setEndereco(atualizacao.getEndereco());
            } else {
                enderecoAtualizador.atualizar(
                        cliente.getEndereco(),
                        atualizacao.getEndereco());
            }
		}

		if (atualizacao.getDocumentos() != null) {
			documentoAtualizador.atualizar(
					cliente.getDocumentos(),
					atualizacao.getDocumentos());
		}

		if (atualizacao.getTelefones() != null) {
			telefoneAtualizador.atualizar(
					cliente.getTelefones(),
					atualizacao.getTelefones());
		}
	}

}

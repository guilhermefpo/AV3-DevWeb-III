package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;

@Component
public class DocumentoAtualizador {
	private final StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Documento documento, Documento atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getTipo())) {
				documento.setTipo(atualizacao.getTipo());
			}
			if (!verificador.verificar(atualizacao.getNumero())) {
				documento.setNumero(atualizacao.getNumero());
			}
		}
	}

	public void atualizar(List<Documento> documentos, List<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
            boolean encontrado = false;
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId().equals(documento.getId())) {
						atualizar(documento, atualizacao);
                        encontrado = true;
					}
				}
			}
            if (!encontrado) {
                throw new IllegalArgumentException("Documento informado não pertence ao cliente");
            }
		}
	}
}

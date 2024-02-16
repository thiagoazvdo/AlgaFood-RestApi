package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, " + "pois está em uso";
	@Autowired
	private CozinhaRepository cozinhas;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhas.save(cozinha);
	}

	public void excluir(Long cozinhaId) throws EntidadeNaoEncontradaException {
		try {
			cozinhas.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) throws EntidadeNaoEncontradaException {
		return cozinhas.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
	
}

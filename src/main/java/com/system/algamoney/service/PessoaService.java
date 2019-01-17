package com.system.algamoney.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.system.algamoney.model.Pessoa;
import com.system.algamoney.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public Pessoa atualizar(Pessoa pessoa, Long codigo) {
		Pessoa pessoaSalva = buscarPorId(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaSalva;
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPorId(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
		
	}
	
	public Pessoa buscarPorId(Long codigo){
		Pessoa pessoaSalva = repository.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}

}

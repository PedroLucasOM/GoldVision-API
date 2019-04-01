package com.system.algamoney.service;

import java.util.List;

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
	
	public List<Pessoa> listarTodos() {
		return repository.findAll();
	}
	
	public Pessoa salvar(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public Pessoa atualizar(Pessoa pessoa, Long codigo) {
		Pessoa pessoaSalva = buscarPessoaExistente(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaSalva;
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaExistente(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
		
	}
	
	public Pessoa listarPorId(Long codigo) {
		return repository.findOne(codigo);
	}
	
	public void deletar(Long codigo) {
		repository.delete(codigo);
	}
	
	public Pessoa buscarPessoaExistente(Long codigo){
		Pessoa pessoaSalva = listarPorId(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
}

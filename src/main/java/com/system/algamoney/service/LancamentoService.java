package com.system.algamoney.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.algamoney.model.Lancamento;
import com.system.algamoney.model.Pessoa;
import com.system.algamoney.repository.LancamentoRepository;
import com.system.algamoney.repository.PessoaRepository;
import com.system.algamoney.service.exception.PessoaInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa.isInativo()) {
			throw new PessoaInativaException();
		}
		return repository.save(lancamento);
	}
	
	public Lancamento atualizar(Lancamento lancamento, Long codigo) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		
		if(!lancamentoSalvo.getPessoa().equals(lancamento.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return repository.save(lancamentoSalvo);
	}
	
	public void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if(lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		}
		
		if(pessoa.isInativo()) {
			throw new PessoaInativaException();
		}
	}

	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = repository.findOne(codigo);
		if (lancamentoSalvo == null) {
			throw new IllegalArgumentException();
		}
		return lancamentoSalvo;
	}
}

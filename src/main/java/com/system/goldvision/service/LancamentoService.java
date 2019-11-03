package com.system.goldvision.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.system.goldvision.model.Lancamento;
import com.system.goldvision.model.Pessoa;
import com.system.goldvision.repository.LancamentoRepository;
import com.system.goldvision.repository.PessoaRepository;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import com.system.goldvision.service.exception.PessoaInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		return repository.filtrar(filter, pageable);
	}
	
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
		return repository.resumir(filter, pageable);
	}
	
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

	public Lancamento listarPorId(Long codigo) {
		return repository.findOne(codigo);
	}

	public void deletar(Long codigo) {
		repository.delete(codigo);
	}
	
	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = listarPorId(codigo);
		if(lancamentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo;
	}
}

package com.system.algamoney.service;

import java.text.Normalizer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.system.algamoney.model.Permissao;
import com.system.algamoney.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository repository;
	
	public Permissao salvar(Permissao permissao) {
		permissao.setNome(formaNomePermissao(permissao.getNome()));
		repository.save(permissao);
		return permissao;
	}
	
	public Permissao atualizar(Permissao permissao, Long codigo) {
		Permissao permissaoSalva = repository.findOne(codigo);
		if(permissaoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(permissao, permissaoSalva, "codigo");
		return repository.save(permissaoSalva);
	}
	
	public String formaNomePermissao(String nome) {
		nome = "ROLE_" + nome.replaceAll(" ", "_").toUpperCase();
		return Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}

package com.system.goldvision.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.goldvision.model.Pessoa;
import com.system.goldvision.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable);
}

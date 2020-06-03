package com.system.goldvision.repository.pessoa;

import com.system.goldvision.model.Pessoa;
import com.system.goldvision.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {

    Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable);
}

package com.system.algamoney.repository.lancamento;

import java.util.List;

import com.system.algamoney.model.Lancamento;
import com.system.algamoney.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter filter);
}

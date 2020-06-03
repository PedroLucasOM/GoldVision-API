package com.system.goldvision.repository.lancamento;

import com.system.goldvision.model.Lancamento;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);

    Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
}

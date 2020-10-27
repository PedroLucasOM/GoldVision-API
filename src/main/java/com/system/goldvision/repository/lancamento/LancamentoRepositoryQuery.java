package com.system.goldvision.repository.lancamento;

import com.system.goldvision.dto.LancamentoEstatisticaCategoria;
import com.system.goldvision.dto.LancamentoEstatisticaDia;
import com.system.goldvision.model.Lancamento;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepositoryQuery {

    List<LancamentoEstatisticaCategoria> buscarComAgrupamentoPorCategoria(LocalDate mesReferencia);

    List<LancamentoEstatisticaDia> buscarComAgrupamentoPorDia(LocalDate mesReferencia);

    Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);

    Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
}

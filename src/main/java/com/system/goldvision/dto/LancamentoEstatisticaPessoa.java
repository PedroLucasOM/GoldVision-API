package com.system.goldvision.dto;

import com.system.goldvision.model.Pessoa;
import com.system.goldvision.model.TipoLancamento;

import java.math.BigDecimal;

public class LancamentoEstatisticaPessoa {

    private TipoLancamento tipoLancamento;

    private Pessoa pessoa;

    private BigDecimal total;

    public LancamentoEstatisticaPessoa(TipoLancamento tipoLancamento, Pessoa pessoa, BigDecimal total) {
        this.tipoLancamento = tipoLancamento;
        this.pessoa = pessoa;
        this.total = total;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}

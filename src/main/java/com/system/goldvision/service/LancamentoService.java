package com.system.goldvision.service;

import com.system.goldvision.dto.LancamentoEstatisticaCategoria;
import com.system.goldvision.dto.LancamentoEstatisticaDia;
import com.system.goldvision.dto.LancamentoEstatisticaPessoa;
import com.system.goldvision.model.Lancamento;
import com.system.goldvision.model.Pessoa;
import com.system.goldvision.repository.LancamentoRepository;
import com.system.goldvision.repository.PessoaRepository;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import com.system.goldvision.service.exception.PessoaInativaException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    public List<LancamentoEstatisticaCategoria> buscarComAgrupamentoPorCategoria() {
        return this.repository.buscarComAgrupamentoPorCategoria(LocalDate.now());
    }

    public List<LancamentoEstatisticaDia> buscarComAgrupamentoPorDia() {
        return this.repository.buscarComAgrupamentoPorDia(LocalDate.now());
    }

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        if (pessoa.isInativo()) {
            throw new PessoaInativaException();
        }
        return repository.save(lancamento);
    }

    public Lancamento atualizar(Lancamento lancamento, Long codigo) {
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
        validarPessoa(lancamento);

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
        return repository.save(lancamentoSalvo);
    }

    public void validarPessoa(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        if (pessoa != null & pessoa.isInativo()) {
            throw new PessoaInativaException();
        }
    }

    public Lancamento listarPorId(Long codigo) {
        return repository.findOne(codigo);
    }

    public void deletar(Long codigo) {
        repository.delete(codigo);
    }

    public byte[] gerarBytesDoRelatorio(LocalDate inicio, LocalDate fim) throws JRException {
        List<LancamentoEstatisticaPessoa> lancamentoEstatisticaPessoas = this.repository.buscarComAgrupamentoPorPessoa(inicio, fim);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("DT_INICIO", Date.valueOf(inicio));
        parametros.put("DT_FIM", Date.valueOf(fim));
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamento/lancamentos-por-pessoa.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(lancamentoEstatisticaPessoas));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private Lancamento buscarLancamentoExistente(Long codigo) {
        Lancamento lancamentoSalvo = listarPorId(codigo);
        if (lancamentoSalvo == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return lancamentoSalvo;
    }
}

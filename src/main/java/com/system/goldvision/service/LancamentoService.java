package com.system.goldvision.service;

import com.google.cloud.storage.Blob;
import com.system.goldvision.dto.LancamentoEstatisticaCategoria;
import com.system.goldvision.dto.LancamentoEstatisticaDia;
import com.system.goldvision.dto.LancamentoEstatisticaPessoa;
import com.system.goldvision.mail.Mailer;
import com.system.goldvision.model.Lancamento;
import com.system.goldvision.model.Pessoa;
import com.system.goldvision.model.Usuario;
import com.system.goldvision.repository.LancamentoRepository;
import com.system.goldvision.repository.PessoaRepository;
import com.system.goldvision.repository.UsuarioRepository;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import com.system.goldvision.service.exception.PessoaInativaException;
import com.system.goldvision.storage.GoogleCloudStorage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class LancamentoService {

    private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);
    private static final String DESTINATARIOS = "LISTAR_LANCAMENTO";

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Mailer mailer;

    @Autowired
    private GoogleCloudStorage googleCloudStorage;

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

        if (StringUtils.hasText(lancamento.getAnexo())) {
            String novoAnexo = this.googleCloudStorage.salvar(lancamento.getAnexo());
            lancamento.setAnexo(novoAnexo);
        }

        return repository.save(lancamento);
    }

    public Lancamento atualizar(Lancamento lancamento, Long codigo) {
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
        validarPessoa(lancamento);

        if (StringUtils.isEmpty(lancamento.getAnexo())
                && StringUtils.hasText(lancamentoSalvo.getAnexo())) {
            this.googleCloudStorage.remover(lancamentoSalvo.getAnexo());
        } else if (StringUtils.hasText(lancamento.getAnexo()) &&
                !lancamento.getAnexo().equals(lancamentoSalvo.getAnexo())) {
            String anexoNovo = this.googleCloudStorage.substituir(lancamentoSalvo.getAnexo(), lancamento.getAnexo());
            lancamento.setAnexo(anexoNovo);
        }

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
        Lancamento lancamento = this.buscarLancamentoExistente(codigo);
        if (StringUtils.hasText(lancamento.getAnexo())) {
            this.googleCloudStorage.remover(lancamento.getAnexo());
        }
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

    @Scheduled(cron = "0 0 0 * * *")
    public void avisarSobreLancamentosVencidos() {
        if (logger.isDebugEnabled()) {
            logger.debug("Preparando envio de "
                    + "e-mails de aviso de lançamentos vencidos.");
        }

        List<Lancamento> vencidos = repository
                .findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());

        if (vencidos.isEmpty()) {
            logger.info("Sem lançamentos vencidos para aviso.");

            return;
        }

        logger.info("Exitem {} lançamentos vencidos.", vencidos.size());

        List<Usuario> destinatarios = usuarioRepository
                .findByPermissoesNome(DESTINATARIOS);

        if (destinatarios.isEmpty()) {
            logger.warn("Existem lançamentos vencidos, mas o "
                    + "sistema não encontrou destinatários.");

            return;
        }

        mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);

        logger.info("Envio de e-mail de aviso concluído.");
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void excluirArquivosInuteis() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Iterable<Blob> arquivos = this.googleCloudStorage.buscarArquivosInuteis().iterateAll();

        arquivos.forEach(blob -> {
            Long diffMills = timestamp.getTime() - blob.getCreateTime();
            Long diffDays = diffMills / (1000 * 60 * 60 * 24);

            if (diffDays >= 1) {
                try {
                    this.googleCloudStorage.remover(blob.getName());
                    logger.info("Exclusão de arquivo {} feita com sucesso!", blob.getName());
                } catch (Exception ex) {
                    logger.error("Não foi possível excluir o arquivo {}.", blob.getName());
                }
            }
        });
    }
}

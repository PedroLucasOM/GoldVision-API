package com.system.goldvision.repository.lancamento;

import com.system.goldvision.dto.LancamentoEstatisticaCategoria;
import com.system.goldvision.dto.LancamentoEstatisticaDia;
import com.system.goldvision.dto.LancamentoEstatisticaPessoa;
import com.system.goldvision.model.Categoria_;
import com.system.goldvision.model.Lancamento;
import com.system.goldvision.model.Lancamento_;
import com.system.goldvision.model.Pessoa_;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<LancamentoEstatisticaPessoa> buscarComAgrupamentoPorPessoa(LocalDate inicio, LocalDate fim) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaPessoa> criteriaQuery = criteriaBuilder
                .createQuery(LancamentoEstatisticaPessoa.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaPessoa.class,
                root.get(Lancamento_.tipo),
                root.get(Lancamento_.pessoa),
                criteriaBuilder.sum(root.get(Lancamento_.valor))));

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), inicio));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), fim));

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        criteriaQuery.groupBy(root.get(Lancamento_.tipo), root.get(Lancamento_.pessoa));

        TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public List<LancamentoEstatisticaCategoria> buscarComAgrupamentoPorCategoria(LocalDate mesReferencia) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaCategoria> criteriaQuery = criteriaBuilder
                .createQuery(LancamentoEstatisticaCategoria.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaCategoria.class,
                root.get(Lancamento_.categoria),
                criteriaBuilder.sum(root.get(Lancamento_.valor))));

        LocalDate primeiroDiaMes = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDiaMes = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDiaMes));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDiaMes));

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        criteriaQuery.groupBy(root.get(Lancamento_.categoria));

        TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public List<LancamentoEstatisticaDia> buscarComAgrupamentoPorDia(LocalDate mesReferencia) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaDia> criteriaQuery = criteriaBuilder
                .createQuery(LancamentoEstatisticaDia.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaDia.class,
                root.get(Lancamento_.tipo),
                root.get(Lancamento_.dataVencimento),
                criteriaBuilder.sum(root.get(Lancamento_.valor))));

        LocalDate primeiroDiaMes = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDiaMes = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDiaMes));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDiaMes));

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        criteriaQuery.groupBy(root.get(Lancamento_.tipo), root.get(Lancamento_.dataVencimento));

        TypedQuery<LancamentoEstatisticaDia> typedQuery = manager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(builder.construct(ResumoLancamento.class
                , root.get(Lancamento_.codigo), root.get(Lancamento_.descricao)
                , root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento)
                , root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
                , root.get(Lancamento_.categoria).get(Categoria_.nome)
                , root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(filter.getDescricao())) {
            predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), "%" + filter.getDescricao().toLowerCase() + "%"));
        }

        if (filter.getDataVencimentoDe() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
        }

        if (filter.getDataVencimentoAte() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(LancamentoFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);

        criteria.where(predicates);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}

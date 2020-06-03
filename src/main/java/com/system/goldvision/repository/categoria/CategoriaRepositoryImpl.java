package com.system.goldvision.repository.categoria;

import com.system.goldvision.model.Categoria;
import com.system.goldvision.model.Categoria_;
import com.system.goldvision.repository.filter.CategoriaFilter;
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
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Categoria> filtrar(CategoriaFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);

        Root<Categoria> root = criteria.from(Categoria.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<Categoria> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private Predicate[] criarRestricoes(CategoriaFilter filter, CriteriaBuilder builder, Root<Categoria> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(filter.getNome())) {
            predicates.add(builder.like(builder.lower(root.get(Categoria_.nome)), "%" + filter.getNome().toLowerCase() + "%"));
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

    private Long total(CategoriaFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Categoria> root = criteria.from(Categoria.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);

        criteria.where(predicates);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}

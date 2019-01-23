package com.system.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.algamoney.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

}

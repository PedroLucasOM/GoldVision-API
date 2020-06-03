package com.system.goldvision.repository;

import com.system.goldvision.model.Categoria;
import com.system.goldvision.repository.categoria.CategoriaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuery {

}

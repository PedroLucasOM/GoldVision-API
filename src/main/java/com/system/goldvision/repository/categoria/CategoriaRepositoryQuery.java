package com.system.goldvision.repository.categoria;

import com.system.goldvision.model.Categoria;
import com.system.goldvision.repository.filter.CategoriaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepositoryQuery {

    Page<Categoria> filtrar(CategoriaFilter categoriaFilter, Pageable pageable);

}

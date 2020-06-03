package com.system.goldvision.repository.usuario;

import com.system.goldvision.model.Usuario;
import com.system.goldvision.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepositoryQuery {

    Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
}

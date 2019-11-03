package com.system.goldvision.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.goldvision.model.Usuario;
import com.system.goldvision.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
}

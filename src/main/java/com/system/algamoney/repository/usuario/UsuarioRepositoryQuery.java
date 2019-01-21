package com.system.algamoney.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.algamoney.model.Usuario;
import com.system.algamoney.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
}

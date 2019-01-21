package com.system.algamoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.algamoney.model.Usuario;
import com.system.algamoney.repository.usuario.UsuarioRepositoryQuery;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery{

	public Optional<Usuario> findByEmail(String email);
}

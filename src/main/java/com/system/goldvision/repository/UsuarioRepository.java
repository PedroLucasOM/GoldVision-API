package com.system.goldvision.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.goldvision.model.Usuario;
import com.system.goldvision.repository.usuario.UsuarioRepositoryQuery;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery{

	public Optional<Usuario> findByEmail(String email);
}

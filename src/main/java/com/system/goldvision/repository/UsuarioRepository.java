package com.system.goldvision.repository;

import com.system.goldvision.model.Usuario;
import com.system.goldvision.repository.usuario.UsuarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPermissoesNome(String permissaoNome);
}

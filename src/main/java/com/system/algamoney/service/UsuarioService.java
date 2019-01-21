package com.system.algamoney.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.system.algamoney.model.Usuario;
import com.system.algamoney.repository.UsuarioRepository;
import com.system.algamoney.service.exception.UsuarioExistenteException;
import com.system.algamoney.util.ResponseUtil;

public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ResponseUtil util;
	
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioOpcional = repository.findByEmail(usuario.getEmail());
		if(usuarioOpcional.isPresent()) {
			throw new UsuarioExistenteException();
		}
		usuario.setSenha(util.gerarSenhaCriptografada(usuario.getSenha()));
		return repository.save(usuario);
		
	}
	
	public Usuario atualizar(Usuario usuario, Long codigo) {
		Usuario usuarioSalvo = repository.findOne(codigo);
		if(usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		return repository.save(usuarioSalvo);
	}

}

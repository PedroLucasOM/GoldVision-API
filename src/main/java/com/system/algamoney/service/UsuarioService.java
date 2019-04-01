package com.system.algamoney.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.system.algamoney.model.Usuario;
import com.system.algamoney.repository.UsuarioRepository;
import com.system.algamoney.repository.filter.UsuarioFilter;
import com.system.algamoney.service.exception.UsuarioExistenteException;
import com.system.algamoney.util.ResponseUtil;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ResponseUtil util;
	
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		return repository.filtrar(filter, pageable);
	}
	
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioOpcional = repository.findByEmail(usuario.getEmail());
		if(usuarioOpcional.isPresent()) {
			throw new UsuarioExistenteException();
		}
		usuario.setSenha(util.gerarSenhaCriptografada(usuario.getSenha()));
		return repository.save(usuario);
		
	}
	
	public Usuario atualizar(Usuario usuario, Long codigo) {
		Usuario usuarioSalvo = buscarUsuarioExistente(codigo);
		if(usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		return repository.save(usuarioSalvo);
	}

	public Usuario listarPorId(Long codigo) {
		return repository.findOne(codigo);
	}

	public void deletar(Long codigo) {
		repository.delete(codigo);
		
	}

	public Usuario buscarUsuarioExistente(Long codigo) {
		Usuario usuarioSalvo = listarPorId(codigo);
		if(usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioSalvo;
	}
}

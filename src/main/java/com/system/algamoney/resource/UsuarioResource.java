package com.system.algamoney.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.system.algamoney.event.RecursoCriadoEvent;
import com.system.algamoney.model.Usuario;
import com.system.algamoney.repository.UsuarioRepository;
import com.system.algamoney.repository.filter.UsuarioFilter;
import com.system.algamoney.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public Page<Usuario> pesquisar(UsuarioFilter filter, Pageable pageable){
		return repository.filtrar(filter, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
		Usuario usuarioSalvo = service.salvar(usuario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable Long codigo){
		Usuario usuario = repository.findOne(codigo);
		return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuario, @PathVariable Long codigo){
		Usuario usuarioSalvo = service.atualizar(usuario, codigo);
		return ResponseEntity.ok(usuarioSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		repository.delete(codigo);
	}
}

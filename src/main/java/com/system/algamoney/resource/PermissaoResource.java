package com.system.algamoney.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.system.algamoney.model.Permissao;
import com.system.algamoney.repository.PermissaoRepository;
import com.system.algamoney.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoResource {

	@Autowired
	private PermissaoRepository repository;
	
	@Autowired
	private PermissaoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Permissao> listar(){
		return repository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Permissao> salvar(@Valid @RequestBody Permissao permissao, HttpServletResponse response){
		Permissao permissaoSalva = service.salvar(permissao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, permissao.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Permissao> listarPorId(@PathVariable Long codigo){
		Permissao permissao = repository.findOne(codigo);
		return permissao != null ? ResponseEntity.ok(permissao) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Permissao> atualizar(@Valid @RequestBody Permissao permissao, @PathVariable Long codigo){
		Permissao permissaoSalva = service.atualizar(permissao, codigo);
		return ResponseEntity.ok(permissaoSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		repository.delete(codigo);
	}
}

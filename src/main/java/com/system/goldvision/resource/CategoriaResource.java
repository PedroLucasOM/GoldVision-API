package com.system.goldvision.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.system.goldvision.event.RecursoCriadoEvent;
import com.system.goldvision.model.Categoria;
import com.system.goldvision.service.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/categorias")
@Api(value = "Categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('LISTAR_CATEGORIA') and #oauth2.hasScope('read')")
	@ApiOperation(value = "Listar todas as categorias")
	public List<Categoria> listar(){
		return service.listarTodos();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('SALVAR_CATEGORIA') and #oauth2.hasScope('write')")
	@ApiOperation(value = "Cadastrar uma nova categoria")
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva = service.salvar(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('LISTAR_CATEGORIA') and #oauth2.hasScope('read')")
	@ApiOperation(value = "Buscar uma categoria por id")
	public ResponseEntity<Categoria> listarPorId(@PathVariable Long codigo){
		Categoria categoria = service.listarPorId(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ATUALIZAR_CATEGORIA') and #oauth2.hasScope('write')")
	@ApiOperation(value = "Atualizar uma categoria existente")
	public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria, @PathVariable Long codigo){
		Categoria categoriaSalva = service.atualizar(categoria, codigo);
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('DELETAR_CATEGORIA') and #oauth2.hasScope('write')")
	@ApiOperation(value = "Excluir uma categoria existente")
	public void deletar(@PathVariable Long codigo) {
		service.deletar(codigo);
	}
}

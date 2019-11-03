package com.system.goldvision.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.system.goldvision.event.RecursoCriadoEvent;
import com.system.goldvision.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.system.goldvision.model.Lancamento;
import com.system.goldvision.repository.filter.LancamentoFilter;
import com.system.goldvision.repository.lancamento.projection.ResumoLancamento;
import com.system.goldvision.service.LancamentoService;
import com.system.goldvision.service.exception.PessoaInativaException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lancamentos")
@Api(value = "Lançamentos")
public class LancamentoResource {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('LISTAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@ApiOperation(value = "Filtrar lançamentos")
	public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable){
		return service.filtrar(filter, pageable);
	}
	
	@GetMapping(params = "resumir")
	@PreAuthorize("hasAuthority('LISTAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@ApiOperation(value = "Resumir listagem de lançamentos")
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable){
		return service.resumir(filter, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('SALVAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@ApiOperation(value = "Cadastrar um novo lançamento")
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = service.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('LISTAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@ApiOperation(value = "Buscar um lançamento por id")
	public ResponseEntity<Lancamento> listarPorId(@PathVariable Long codigo){
		Lancamento lancamento = service.listarPorId(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ATUALIZAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@ApiOperation(value = "Atualizar um lançamento existente")
	public ResponseEntity<Lancamento> atualizar(@Valid @RequestBody Lancamento lancamento, @PathVariable Long codigo){
		return ResponseEntity.ok(service.atualizar(lancamento, codigo));
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('DELETAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@ApiOperation(value = "Excluir um lançamento existente")
	public void deletar(@PathVariable Long codigo) {
		service.deletar(codigo);
	}
	
	@ExceptionHandler({PessoaInativaException.class})
	public ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}

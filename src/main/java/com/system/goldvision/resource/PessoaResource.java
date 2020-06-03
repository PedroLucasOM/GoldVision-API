package com.system.goldvision.resource;

import com.system.goldvision.event.RecursoCriadoEvent;
import com.system.goldvision.model.Pessoa;
import com.system.goldvision.repository.filter.PessoaFilter;
import com.system.goldvision.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/pessoas")
@Api(value = "Pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('LISTAR_PESSOA') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Filtrar pessoas")
    public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable) {
        return service.filtrar(filter, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SALVAR_PESSOA') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Cadastrar uma nova pessoa")
    public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = service.salvar(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('LISTAR_PESSOA') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Buscar uma pessoa por id")
    public ResponseEntity<Pessoa> listarPorId(@PathVariable Long codigo) {
        Pessoa pessoa = service.listarPorId(codigo);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ATUALIZAR_PESSOA') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Atualizar uma pessoa existente")
    public ResponseEntity<Pessoa> atualizar(@Valid @RequestBody Pessoa pessoa, @PathVariable Long codigo) {
        Pessoa pessoaSalva = service.atualizar(pessoa, codigo);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ATUALIZAR_PESSOA') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Atualizar propriedade ativo de pessoa")
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
        service.atualizarPropriedadeAtivo(codigo, ativo);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('DELETAR_PESSOA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Excluir uma pessoa existente")
    public void deletar(@PathVariable Long codigo) {
        service.deletar(codigo);
    }
}

package com.system.goldvision.resource;

import com.system.goldvision.event.RecursoCriadoEvent;
import com.system.goldvision.model.Permissao;
import com.system.goldvision.service.PermissaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissoes")
@Api(value = "Permissões")
public class PermissaoResource {

    @Autowired
    private PermissaoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('LISTAR_PERMISSAO') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Listar todas as permissões")
    public List<Permissao> listar() {
        return service.listarTodos();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SALVAR_PERMISSAO') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Cadastrar uma nova permissão")
    public ResponseEntity<Permissao> salvar(@Valid @RequestBody Permissao permissao, HttpServletResponse response) {
        Permissao permissaoSalva = service.salvar(permissao);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, permissao.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('LISTAR_PERMISSAO') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Buscar uma permissão por id")
    public ResponseEntity<Permissao> listarPorId(@PathVariable Long codigo) {
        Permissao permissao = service.listarPorId(codigo);
        return permissao != null ? ResponseEntity.ok(permissao) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ATUALIZAR_PERMISSAO') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Atualizar uma permissão existente")
    public ResponseEntity<Permissao> atualizar(@Valid @RequestBody Permissao permissao, @PathVariable Long codigo) {
        Permissao permissaoSalva = service.atualizar(permissao, codigo);
        return ResponseEntity.ok(permissaoSalva);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETAR_PERMISSAO') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Excluir uma permissão existente")
    public void deletar(@PathVariable Long codigo) {
        service.deletar(codigo);
    }
}

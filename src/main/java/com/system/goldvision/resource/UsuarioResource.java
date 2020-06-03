package com.system.goldvision.resource;

import com.system.goldvision.event.RecursoCriadoEvent;
import com.system.goldvision.model.Usuario;
import com.system.goldvision.repository.filter.UsuarioFilter;
import com.system.goldvision.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Api(value = "Usuários")
public class UsuarioResource {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UsuarioService service;

    @GetMapping
    @ApiOperation(value = "Filtrar usuários")
    public Page<Usuario> pesquisar(UsuarioFilter filter, Pageable pageable) {
        return service.filtrar(filter, pageable);
    }

    @PostMapping
    @ApiOperation(value = "Cadastrar um novo usuário")
    public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
        Usuario usuarioSalvo = service.salvar(usuario);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/{codigo}")
    @ApiOperation(value = "Buscar um usuário por id")
    public ResponseEntity<Usuario> listarPorId(@PathVariable Long codigo) {
        Usuario usuario = service.listarPorId(codigo);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{codigo}")
    @ApiOperation(value = "Atualizar um usuário existente")
    public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuario, @PathVariable Long codigo) {
        Usuario usuarioSalvo = service.atualizar(usuario, codigo);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('DELETAR_USUARIO') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Excluir um usuário existente")
    public void excluir(@PathVariable Long codigo) {
        service.deletar(codigo);
    }
}

package com.system.goldvision.resource;

import com.system.goldvision.model.Permissao;
import com.system.goldvision.service.PermissaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
@Api(value = "Permissões")
public class PermissaoResource {

    @Autowired
    private PermissaoService service;

    @GetMapping
    @PreAuthorize("hasAuthority('LISTAR_PERMISSAO') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Listar todas as permissões")
    public List<Permissao> listar() {
        return service.listarTodos();
    }
}

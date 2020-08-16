package com.system.goldvision.service;

import com.system.goldvision.model.Permissao;
import com.system.goldvision.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository repository;

    public List<Permissao> listarTodos() {
        return repository.findAll();
    }
}

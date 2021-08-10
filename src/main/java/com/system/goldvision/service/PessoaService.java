package com.system.goldvision.service;

import com.system.goldvision.model.Pessoa;
import com.system.goldvision.repository.PessoaRepository;
import com.system.goldvision.repository.filter.PessoaFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable) {
        return repository.filtrar(filter, pageable);
    }

    public Pessoa salvar(Pessoa pessoa) {
        if (CollectionUtils.isNotEmpty(pessoa.getContatos())) {
            pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
        }
        return repository.save(pessoa);
    }

    public Pessoa atualizar(Pessoa pessoa, Long codigo) {
        Pessoa pessoaSalva = buscarPessoaExistente(codigo);

        pessoaSalva.getContatos().clear();
        pessoaSalva.getContatos().addAll(pessoa.getContatos());
        pessoaSalva.getContatos().forEach(c -> c.setPessoa(pessoaSalva));

        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo", "contatos");
        return repository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaSalva = buscarPessoaExistente(codigo);
        pessoaSalva.setAtivo(ativo);
        repository.save(pessoaSalva);

    }

    public Pessoa listarPorId(Long codigo) {
        return repository.findOne(codigo);
    }

    public void deletar(Long codigo) {
        repository.delete(codigo);
    }

    public Pessoa buscarPessoaExistente(Long codigo) {
        Pessoa pessoaSalva = listarPorId(codigo);
        if (pessoaSalva == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }
}

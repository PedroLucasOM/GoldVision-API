package com.system.goldvision.service;

import com.system.goldvision.model.Categoria;
import com.system.goldvision.repository.CategoriaRepository;
import com.system.goldvision.repository.filter.CategoriaFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Page<Categoria> filtrar(CategoriaFilter filter, Pageable pageable) {
        return repository.filtrar(filter, pageable);
    }

    public Categoria salvar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria atualizar(Categoria categoria, Long codigo) {
        Categoria categoriaSalva = buscarCategoriaExistente(codigo);
        BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
        return repository.save(categoriaSalva);
    }

    public Categoria listarPorId(Long codigo) {
        return repository.findOne(codigo);
    }

    public void deletar(Long codigo) {
        repository.delete(codigo);
    }

    private Categoria buscarCategoriaExistente(Long codigo) {
        Categoria categoriaSalva = listarPorId(codigo);
        if (categoriaSalva == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return categoriaSalva;
    }
}

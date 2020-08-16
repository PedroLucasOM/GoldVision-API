delete from usuario_permissao where codigo_permissao in
    (select
        codigo
    from permissao
    where nome like 'SALVAR_PERMISSAO' or nome like 'ATUALIZAR_PERMISSAO' or  nome like 'DELETAR_PERMISSAO');

delete from permissao where nome like 'SALVAR_PERMISSAO' or nome like 'ATUALIZAR_PERMISSAO' or  nome like 'DELETAR_PERMISSAO';
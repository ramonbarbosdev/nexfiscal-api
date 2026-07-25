insert into permissao (id_permissao, nm_permissao, ds_permissao, nm_chave)
values
    (1, 'Visualizar configurações', 'Permite visualizar configurações do prestador', 'CONFIG_VIEW'),
    (2, 'Editar configurações', 'Permite editar configurações do prestador', 'CONFIG_EDIT'),
    (3, 'Visualizar propostas', 'Permite visualizar propostas', 'PROPOSTA_VIEW'),
    (4, 'Criar propostas', 'Permite criar propostas', 'PROPOSTA_CREATE'),
    (5, 'Editar propostas', 'Permite editar propostas', 'PROPOSTA_EDIT'),
    (6, 'Visualizar notas fiscais', 'Permite visualizar notas fiscais', 'NOTA_FISCAL_VIEW'),
    (7, 'Criar notas fiscais', 'Permite criar notas fiscais', 'NOTA_FISCAL_CREATE'),
    (8, 'Editar notas fiscais', 'Permite editar notas fiscais', 'NOTA_FISCAL_EDIT'),
    (9, 'Emitir notas fiscais', 'Permite emitir notas fiscais', 'NOTA_FISCAL_EMIT'),
    (10, 'Cancelar notas fiscais', 'Permite cancelar notas fiscais', 'NOTA_FISCAL_CANCEL')
on conflict (nm_chave) do nothing;

insert into papel (id_papel, nm_papel, ds_papel, fl_ativo)
values (1, 'ADMIN', 'Administrador do sistema', true)
on conflict do nothing;

insert into papel_permissao (id_papel_permissao, id_papel, id_permissao)
values
    (1, 1, 1),
    (2, 1, 2),
    (3, 1, 3),
    (4, 1, 4),
    (5, 1, 5),
    (6, 1, 6),
    (7, 1, 7),
    (8, 1, 8),
    (9, 1, 9),
    (10, 1, 10)
on conflict do nothing;

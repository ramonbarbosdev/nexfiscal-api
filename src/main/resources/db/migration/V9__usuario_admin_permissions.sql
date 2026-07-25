insert into permissao (id_permissao, nm_permissao, ds_permissao, nm_chave)
values
    (11, 'Visualizar usuários', 'Permite acessar a área de administração de usuários', 'USER_VIEW'),
    (12, 'Criar usuários', 'Permite cadastrar novos usuários', 'USER_CREATE'),
    (13, 'Editar usuários', 'Permite editar usuários existentes', 'USER_EDIT'),
    (14, 'Excluir usuários', 'Permite desativar usuários', 'USER_DELETE')
on conflict (nm_chave) do nothing;

insert into papel_permissao (id_papel_permissao, id_papel, id_permissao)
values
    (11, 1, 11),
    (12, 1, 12),
    (13, 1, 13),
    (14, 1, 14)
on conflict do nothing;

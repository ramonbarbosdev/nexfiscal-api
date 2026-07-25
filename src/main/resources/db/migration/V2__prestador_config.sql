create table if not exists prestador_config (
    id_config bigint primary key default 1,
    razao_social varchar(255) not null default '',
    nome_fantasia varchar(255) not null default '',
    cnpj varchar(20) not null default '',
    inscricao_municipal varchar(50) not null default '',
    email varchar(255) not null default '',
    telefone varchar(50) not null default '',
    logradouro varchar(255) not null default '',
    numero varchar(20) not null default '',
    complemento varchar(100) not null default '',
    bairro varchar(100) not null default '',
    cidade varchar(100) not null default '',
    uf varchar(2) not null default '',
    cep varchar(20) not null default '',
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now(),
    constraint ck_prestador_config_singleton check (id_config = 1)
);

insert into prestador_config (id_config)
values (1)
on conflict (id_config) do nothing;

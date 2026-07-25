alter table empresa
    add column if not exists nm_logradouro varchar(255) not null default '',
    add column if not exists ds_numero varchar(50) not null default '',
    add column if not exists ds_complemento varchar(255) not null default '',
    add column if not exists nm_bairro varchar(255) not null default '',
    add column if not exists nm_cidade varchar(255) not null default '',
    add column if not exists sg_uf varchar(2) not null default '',
    add column if not exists ds_cep varchar(20) not null default '';

alter table cliente
    add column if not exists nm_logradouro varchar(255) not null default '',
    add column if not exists ds_numero varchar(50) not null default '',
    add column if not exists ds_complemento varchar(255) not null default '',
    add column if not exists nm_bairro varchar(255) not null default '',
    add column if not exists nm_cidade varchar(255) not null default '',
    add column if not exists sg_uf varchar(2) not null default '',
    add column if not exists ds_cep varchar(20) not null default '';

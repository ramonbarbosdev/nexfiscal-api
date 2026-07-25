alter table empresa
    add column if not exists ds_cnpj varchar(20) not null default '';

alter table cliente
    add column if not exists ds_tipo varchar(2) not null default 'pf';

alter table cliente
    add column if not exists ds_cpf_cnpj varchar(20) not null default '';

create sequence if not exists seq_empresa start with 100 increment by 1;
create sequence if not exists seq_cliente start with 100 increment by 1;

create table if not exists empresa (
    id_empresa bigint primary key,
    ds_logo text not null default '',
    nm_empresa varchar(255) not null,
    ds_whatsapp varchar(50) not null default '',
    ds_instagram varchar(255) not null default '',
    nm_email varchar(255) not null default '',
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now()
);

create index if not exists ix_empresa_nm_empresa on empresa (lower(nm_empresa));

create table if not exists cliente (
    id_cliente bigint primary key,
    nm_cliente varchar(255) not null,
    ds_telefone varchar(50) not null default '',
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now()
);

create index if not exists ix_cliente_nm_cliente on cliente (lower(nm_cliente));
create index if not exists ix_cliente_ds_telefone on cliente (ds_telefone);

create sequence if not exists seq_proposta start with 1 increment by 1;
create sequence if not exists seq_item_proposta start with 1 increment by 1;

create table if not exists proposta_seq_ano (
    nu_ano int primary key,
    nu_seq int not null default 0
);

create table if not exists proposta (
    id_proposta bigint primary key,
    nu_numero varchar(20) not null,
    nu_ano int not null,
    nu_seq int not null,
    ds_status varchar(20) not null default 'pendente'
        check (ds_status in ('pendente', 'aprovada', 'cancelada')),
    ds_empresa_logo text not null default '',
    nm_empresa varchar(255) not null default '',
    ds_empresa_whatsapp varchar(50) not null default '',
    ds_empresa_instagram varchar(100) not null default '',
    nm_empresa_email varchar(255) not null default '',
    nm_cliente varchar(255) not null default '',
    ds_cliente_telefone varchar(50) not null default '',
    nm_projeto_titulo varchar(255) not null default '',
    ds_projeto_descricao text not null default '',
    ds_projeto_area varchar(100) not null default '',
    ds_projeto_prazo varchar(100) not null default '',
    ds_projeto_validade varchar(100) not null default '',
    vl_desconto numeric(15, 2) not null default 0,
    vl_entrada numeric(15, 2) not null default 0,
    ds_forma_pagamento varchar(255) not null default '',
    ds_observacoes text not null default '',
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now()
);

create unique index if not exists ux_proposta_nu_numero on proposta (nu_numero);
create index if not exists ix_proposta_status on proposta (ds_status);
create index if not exists ix_proposta_dt_criacao on proposta (dt_criacao desc);

create table if not exists item_proposta (
    id_item_proposta bigint primary key,
    id_proposta bigint not null references proposta (id_proposta) on delete cascade,
    ds_descricao varchar(500) not null default '',
    qt_quantidade numeric(15, 4) not null default 1,
    vl_unitario numeric(15, 2) not null default 0
);

create index if not exists ix_item_proposta_proposta on item_proposta (id_proposta);

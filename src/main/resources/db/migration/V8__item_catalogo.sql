create sequence if not exists seq_item_catalogo start with 100 increment by 1;

create table if not exists item_catalogo (
    id_item_catalogo bigint primary key,
    ds_tipo varchar(10) not null default 'servico'
        check (ds_tipo in ('produto', 'servico')),
    nm_item varchar(255) not null,
    ds_descricao text not null default '',
    ds_codigo_lc116 varchar(20) not null default '',
    vl_preco_padrao numeric(15, 2) not null default 0,
    vl_aliquota_iss numeric(8, 4) not null default 0,
    sg_iss_retido boolean not null default false,
    ds_unidade varchar(20) not null default 'un',
    ds_codigo_interno varchar(50) not null default '',
    sg_ativo boolean not null default true,
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now()
);

create index if not exists ix_item_catalogo_tipo on item_catalogo (ds_tipo);
create index if not exists ix_item_catalogo_nm on item_catalogo (lower(nm_item));
create index if not exists ix_item_catalogo_ativo on item_catalogo (sg_ativo);

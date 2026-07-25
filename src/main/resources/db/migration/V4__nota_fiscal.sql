create sequence if not exists seq_nota_fiscal start with 1 increment by 1;
create sequence if not exists seq_nota_fiscal_numero start with 1 increment by 1;

create table if not exists nota_fiscal (
    id_nota_fiscal bigint primary key,
    nu_numero varchar(10) not null,
    nu_serie varchar(5) not null default '1',
    ds_status varchar(20) not null default 'rascunho'
        check (ds_status in ('rascunho', 'emitida', 'cancelada')),
    dt_emissao timestamp not null default now(),
    ds_codigo_verificacao varchar(20),
    ds_observacoes text not null default '',
    prest_razao_social varchar(255) not null default '',
    prest_nome_fantasia varchar(255) not null default '',
    prest_cnpj varchar(20) not null default '',
    prest_inscricao_municipal varchar(50) not null default '',
    prest_email varchar(255) not null default '',
    prest_telefone varchar(50) not null default '',
    prest_logradouro varchar(255) not null default '',
    prest_numero varchar(20) not null default '',
    prest_complemento varchar(100) not null default '',
    prest_bairro varchar(100) not null default '',
    prest_cidade varchar(100) not null default '',
    prest_uf varchar(2) not null default '',
    prest_cep varchar(20) not null default '',
    tom_tipo varchar(2) not null default 'pj' check (tom_tipo in ('pf', 'pj')),
    tom_nome varchar(255) not null default '',
    tom_cpf_cnpj varchar(20) not null default '',
    tom_email varchar(255) not null default '',
    tom_telefone varchar(50) not null default '',
    tom_inscricao_municipal varchar(50) not null default '',
    tom_logradouro varchar(255) not null default '',
    tom_numero varchar(20) not null default '',
    tom_complemento varchar(100) not null default '',
    tom_bairro varchar(100) not null default '',
    tom_cidade varchar(100) not null default '',
    tom_uf varchar(2) not null default '',
    tom_cep varchar(20) not null default '',
    srv_codigo_lc116 varchar(20) not null default '',
    srv_descricao varchar(255) not null default '',
    srv_discriminacao text not null default '',
    srv_valor_servico numeric(15, 2) not null default 0,
    srv_aliquota_iss numeric(8, 4) not null default 0,
    srv_iss_retido boolean not null default false,
    srv_valor_deducoes numeric(15, 2) not null default 0,
    srv_desconto_incondicionado numeric(15, 2) not null default 0,
    srv_desconto_condicionado numeric(15, 2) not null default 0,
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now()
);

create unique index if not exists ux_nota_fiscal_numero_serie on nota_fiscal (nu_numero, nu_serie);
create index if not exists ix_nota_fiscal_status on nota_fiscal (ds_status);
create index if not exists ix_nota_fiscal_dt_emissao on nota_fiscal (dt_emissao desc);

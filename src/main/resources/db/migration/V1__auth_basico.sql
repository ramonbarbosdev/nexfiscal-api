create sequence if not exists seq_usuario start with 100 increment by 1;
create sequence if not exists seq_papel start with 100 increment by 1;
create sequence if not exists seq_permissao start with 100 increment by 1;
create sequence if not exists seq_papel_permissao start with 100 increment by 1;
create sequence if not exists seq_usuario_papel start with 100 increment by 1;

create table if not exists usuario (
    id_usuario bigint primary key,
    nm_email varchar(255) not null,
    nm_usuario varchar(255) not null,
    ds_senha varchar(255) not null,
    fl_ativo boolean not null default true,
    dt_criacao timestamp not null default now(),
    dt_atualizacao timestamp not null default now()
);

create unique index if not exists ux_usuario_nm_email on usuario (nm_email);

create table if not exists papel (
    id_papel bigint primary key,
    nm_papel varchar(255) not null,
    ds_papel varchar(255),
    fl_ativo boolean not null default true
);

create unique index if not exists ux_papel_nm_papel on papel (nm_papel);

create table if not exists permissao (
    id_permissao bigint primary key,
    nm_permissao varchar(255) not null,
    ds_permissao varchar(255),
    nm_chave varchar(255) not null unique
);

create table if not exists papel_permissao (
    id_papel_permissao bigint primary key,
    id_papel bigint not null references papel (id_papel),
    id_permissao bigint not null references permissao (id_permissao)
);

create unique index if not exists ux_papel_permissao on papel_permissao (id_papel, id_permissao);

create table if not exists usuario_papel (
    id_usuario_papel bigint primary key,
    id_usuario bigint not null references usuario (id_usuario),
    id_papel bigint not null references papel (id_papel)
);

create unique index if not exists ux_usuario_papel on usuario_papel (id_usuario, id_papel);

alter table proposta
    add column if not exists id_empresa bigint,
    add column if not exists id_cliente bigint;

alter table item_proposta
    add column if not exists id_item_catalogo bigint;

update proposta p
set id_empresa = e.id_empresa
from empresa e
where p.id_empresa is null
  and btrim(p.nm_empresa) <> ''
  and lower(btrim(p.nm_empresa)) = lower(btrim(e.nm_empresa));

update proposta p
set id_cliente = c.id_cliente
from cliente c
where p.id_cliente is null
  and btrim(p.nm_cliente) <> ''
  and lower(btrim(p.nm_cliente)) = lower(btrim(c.nm_cliente))
  and (
    btrim(p.ds_cliente_telefone) = ''
    or btrim(c.ds_telefone) = ''
    or regexp_replace(p.ds_cliente_telefone, '\D', '', 'g')
       = regexp_replace(c.ds_telefone, '\D', '', 'g')
  );

alter table proposta
    add constraint fk_proposta_empresa
        foreign key (id_empresa) references empresa (id_empresa) on delete restrict;

alter table proposta
    add constraint fk_proposta_cliente
        foreign key (id_cliente) references cliente (id_cliente) on delete restrict;

alter table item_proposta
    add constraint fk_item_proposta_item_catalogo
        foreign key (id_item_catalogo) references item_catalogo (id_item_catalogo) on delete restrict;

create index if not exists ix_proposta_id_empresa on proposta (id_empresa);
create index if not exists ix_proposta_id_cliente on proposta (id_cliente);
create index if not exists ix_item_proposta_id_item_catalogo on item_proposta (id_item_catalogo);

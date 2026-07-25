alter table proposta drop constraint if exists proposta_ds_status_check;

alter table proposta
    add constraint proposta_ds_status_check
        check (ds_status in ('pendente', 'aprovada', 'cancelada', 'concluida'));

create table if not exists outbox_mails (
    id bigserial primary key,
    status varchar(30) not null,
    to_email varchar(254) not null,
    subject varchar(150) not null,
    body text not null,
    attempts int not null default 0,
    last_error varchar(500),
    created_at timestamp not null default now(),
    sent_at timestamp
);

create index if not exists idx_outbox_mails_status on outbox_mails (status);

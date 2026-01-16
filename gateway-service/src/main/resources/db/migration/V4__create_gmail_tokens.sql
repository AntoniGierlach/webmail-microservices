create table if not exists gmail_tokens (
    id bigserial primary key,
    email varchar(254) not null unique,
    subject varchar(128) not null,
    access_token text not null,
    refresh_token text,
    expires_at timestamp,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create index if not exists idx_gmail_tokens_email on gmail_tokens (email);

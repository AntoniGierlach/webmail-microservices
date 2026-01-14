create table contacts (
    id bigserial primary key,
    name varchar(120) not null,
    email varchar(254) not null unique,
    phone varchar(40),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create index idx_contacts_name on contacts (name);

create table if not exists sync_jobs (
    id bigserial primary key,
    status varchar(30) not null,
    requested_at timestamp not null default now(),
    finished_at timestamp,
    message varchar(500),
    inbox_count int not null default 0
);

create index if not exists idx_sync_jobs_status on sync_jobs (status);

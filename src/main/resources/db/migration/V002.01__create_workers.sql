create table workers
(
    id                  bigserial primary key,
    name                varchar(255),
    organization_id     bigint,
    leader_id           bigint
);
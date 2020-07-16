create table organizations
(
    id                          bigserial primary key,
    name                        varchar(255),
    organization_header_id      bigint
);
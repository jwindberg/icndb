drop table if exists category cascade;
drop table if exists joke cascade;
drop table if exists joke_categories cascade;

create table category
(
    id   bigint not null,
    data varchar(255),
    primary key (id)
);

create table joke
(
    id         bigint       not null,
    guid       varchar(255) not null,
    created_at varchar(255),
    data       varchar(1000),
    icon_url   varchar(255),
    updated_at varchar(255),
    url        varchar(255),
    primary key (id)
);

create table joke_categories
(
    joke_id       varchar(255) not null,
    categories_id bigint       not null,
    primary key (joke_id, categories_id)
);

alter table if exists joke_categories
    add constraint joke_categories_cat_fk
    foreign key (categories_id)
    references category;

alter table if exists joke_categories
    add constraint joke_categories_joke_fk
    foreign key (joke_id)
    references joke;
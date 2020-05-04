create table customer (
    id int8 not null,
    name varchar(255),
    address varchar(255),
    phone_number varchar(255),
    user_id int8,
    primary key (id)
);

alter table if exists customer
    add constraint customer_user_fk
    foreign key (user_id) references usr;
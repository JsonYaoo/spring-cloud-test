-- auto-generated definition
create table housework
(
  id        bigint(11) auto_increment
    primary key,
  fridge_id bigint(11) not null,
  animal_id bigint(11) not null,
  status_id int(2)     not null
);


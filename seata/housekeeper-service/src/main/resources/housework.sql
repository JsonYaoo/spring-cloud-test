-- auto-generated definition
create table housework
(
  id       bigint(11) auto_increment
    primary key,
  fridgeId bigint(11) not null,
  animalId bigint(11) not null,
  statusId int(2)     not null
);


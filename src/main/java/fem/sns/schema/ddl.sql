create table Member
(
  id int auto_increment,
  email varcher(20) not null,
  nickname varchar(20) not null,
  birthday date not null,
  constraint member_id_uindex
        primary key (id)
);
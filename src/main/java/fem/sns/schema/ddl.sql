create table Member
(
  id int auto_increment,
  email varcher(20) not null,
  nickname varchar(20) not null,
  birthday date not null,
  constraint member_id_uindex
        primary key (id)
);

create table MemberNicknameHistory
(
  id int auto_increment,
  memberId int not null,
  nickname varchar(20) not null,
  createAt datetime not null,
  constraint memberNicknameHistory_id_uindx
        primary key (id)
);

create table Follow (
    id int auto_increment,
    fromMemberId int not null,
    toMemberId int not null,
    createAt datetime not null,
    constraint follow_id_uindx
        primary key (id)
);
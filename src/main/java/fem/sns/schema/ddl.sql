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

create table Post (
      id int auto_increment,
      memberId int not null,
      contents varchar(255) not null,
      createDate date not null,
      createAt datetime not null,
      constraint post_id_uindx
                primary key (id)
);

create index POST__index_member_id
    on Post (memberId);

create index POST__index_create_date
    on Post (createDate);

create index POST__index_member_id_create_date
    on Post (memberId, createAt);


create table timeline
(
    id int auto_increment,
    memberId int not null,
    postID int not null,
    createAt datetime not null,
    constraint Timeline_id_uindex
        primary key (id)
);

alter table POST add column likeCount int;
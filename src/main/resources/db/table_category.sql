
drop table if exists category CASCADE;


drop sequence if exists seq_category;
create sequence seq_category start 1 increment 1;


create table category (
  id int not null default nextval('seq_category'),
  category_label varchar(64),
  category_state varchar(25),
  category_totalCost float,
  primary key (id)
);


drop table if exists sub_category CASCADE;

drop sequence if exists sub_category;
create sequence sub_category start 1 increment 1;


create table sub_category (
  id int not null default nextval('seq_sub_category'),
  sub_category_label varchar(64),
  sub_category_state varchar(25),
  sub_category_totalCost float,
  category_id int,
  primary key (id)
);



alter table sub_category add constraint FK_sub_category_category foreign key (category_id) references category;
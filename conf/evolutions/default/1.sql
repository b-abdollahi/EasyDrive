# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ad (
  id                        bigint not null,
  location_id               bigint,
  car_id                    bigint,
  user_id                   bigint,
  description               varchar(2000),
  date                      timestamp,
  picture                   blob,
  constraint pk_ad primary key (id))
;

create table car (
  id                        bigint not null,
  manufacturer              varchar(255),
  model                     varchar(255),
  description               varchar(255),
  constraint pk_car primary key (id))
;

create table location (
  id                        bigint not null,
  city                      varchar(255),
  state                     varchar(255),
  constraint pk_location primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  name                      varchar(255),
  tel                       varchar(255),
  constraint pk_user primary key (id))
;

create sequence ad_seq;

create sequence car_seq;

create sequence location_seq;

create sequence user_seq;

alter table ad add constraint fk_ad_location_1 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_ad_location_1 on ad (location_id);
alter table ad add constraint fk_ad_car_2 foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_ad_car_2 on ad (car_id);
alter table ad add constraint fk_ad_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_ad_user_3 on ad (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists ad;

drop table if exists car;

drop table if exists location;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists ad_seq;

drop sequence if exists car_seq;

drop sequence if exists location_seq;

drop sequence if exists user_seq;


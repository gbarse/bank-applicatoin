create table t_bank_users
(
  id           uuid primary key                                   not null,
  first_name   varchar(25)                                        not null,
  last_name    varchar(25)                                        not null,
  address      varchar(25)                                        not null,
  phone_number varchar(25)                                        not null,
  city         varchar(20)                                        not null,
  birth_date   date                                               not null,
  email        varchar                                            not null,
  password     varchar(256)                                       not null,


  created      timestamp with time zone default current_timestamp not null,
  updated      timestamp with time zone default current_timestamp not null,
  deleted      timestamp with time zone default current_timestamp
);
create table t_customer_users
(
  id           uuid primary key                                   not null,
  first_name   varchar(25)                                        not null,
  last_name    varchar(25)                                        not null,
  address      varchar(25)                                        not null,
  phone_number varchar(25)                                        not null,
  city         varchar(20)                                        not null,
  birth_date   date                                               not null,
  email        varchar(25)                                        not null,
  password     varchar(256)                                       not null,


  created      timestamp with time zone default current_timestamp not null,
  updated      timestamp with time zone default current_timestamp not null,
  deleted      timestamp with time zone default current_timestamp
);

CREATE TABLE t_permissions
(
  id      UUID primary key,
  name    varchar                                            not null,
  created timestamp with time zone default current_timestamp not null,
  updated timestamp with time zone default current_timestamp not null,
  deleted TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp null

);



create table t_roles
(
  id      uuid primary key                                   not null,
  name    varchar(25)                                        not null,
  created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  deleted TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null


);

create table t_roles_permissions
(

  role_id        uuid NOT NULL,
  permissions_id uuid NOT NULL,
  CONSTRAINT role_perm_fk FOREIGN KEY (role_id) REFERENCES t_roles (id),
  CONSTRAINT user_perm_fk FOREIGN KEY (permissions_id) REFERENCES t_permissions (id)
);


alter table t_customer_users
  add constraint customer_uk_email_deleted unique (email, deleted);

alter table t_bank_users
  add constraint bank_uk_email_deleted unique (email, deleted);

create table t_bank_users_role
(
  bank_user_id uuid NOT NULL,
  role_id      uuid NOT NULL,
  CONSTRAINT user_fk FOREIGN KEY (bank_user_id) REFERENCES t_bank_users (id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES t_roles (id)
);

create table t_customer_users_role
(
  customer_user_id uuid NOT NULL,
  role_id          uuid NOT NULL,
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES t_roles (id),
  CONSTRAINT user_fk FOREIGN KEY (customer_user_id) REFERENCES t_customer_users (id)
);


insert into t_roles (id, name, deleted)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'ADMIN', null);

insert into t_roles (id, name, deleted)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', 'CUSTOMER', null);



insert into t_permissions (id, name, deleted)
values ('a57a580a-18f7-4314-a77c-757d58bcbfa0', 'can_create_customer_C', null);

insert into t_permissions (id, name, deleted)
values ('6ced8f4b-4200-4bc9-bf95-74f5eb56d4ad', 'can_view_all_customer_B', null);

insert into t_permissions (id, name, deleted)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'can_update_customer_C', null);

insert into t_permissions (id, name, deleted)
values ('06b8b07f-b49b-476e-adb9-9b814ce25e27', 'can_create_admin_B', null);

insert into t_permissions (id, name, deleted)
values ('be6743b2-0879-40f1-98f9-58fe60492010', 'can_delete_admin_B', null);

insert into t_permissions (id, name, deleted)
values ('0705d724-48ce-4376-8940-8653a3b55dd7', 'can_view_all_admin_B', null);

insert into t_permissions (id, name, deleted)
values ('183a2f1b-b560-45bc-81d5-cc3a1f7e78df', 'can_update_admin_B', null);

insert into t_permissions (id, name, deleted)
values ('0e86acc4-41c7-4aa6-a57f-9587865277e9', 'can_recover_admin_B', null);

insert into t_permissions (id, name, deleted)
values ('3643d4ae-1afe-4b3f-bfc1-1741065bc931', 'can_view_all_admin_by_id_B', null);

insert into t_permissions (id, name, deleted)
values ('196c47eb-9e34-4170-bb52-e19a6050e888', 'can_recover_customer_B', null);

insert into t_permissions (id, name, deleted)
values ('07c60a41-0271-4c87-b24e-8a2f907bf638', 'can_view_all_customer_by_id_B', null);

insert into t_permissions (id, name, deleted)
values ('d773ecab-aac3-457d-ba8d-88ecd68d276f', 'can_create_roles_B', null);

insert into t_permissions (id, name, deleted)
values ('f54ba5cd-b7d8-499c-b09e-0b04b8ef11da', 'can_delete_roles_B', null);

insert into t_permissions (id, name, deleted)
values ('f8c492e4-097f-4573-ae77-c630639dd2c1', 'can_view_roles_B', null);

insert into t_permissions (id, name, deleted)
values ('e6afe944-981e-4cfb-8fe7-a82f2675182d', 'can_update_roles_B', null);

insert into t_permissions (id, name, deleted)
values ('88642a19-087d-40fa-9e02-9a2a2395570c', 'can_delete_customer_B', null);

insert into t_permissions (id, name, deleted)
values ('8b685c0c-750b-4039-a18f-9a984fee1a4d', 'can_recover_roles_B', null);

insert into t_permissions (id, name, deleted)
values ('7231feb2-9e98-4d81-928c-01cddd6749f8', 'can_assign_role_B', null);

insert into t_permissions (id, name, deleted)
values ('a6e02404-e2db-4882-b754-65c0f4117f7c', 'can_assign_permissions_B', null);



insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '6ced8f4b-4200-4bc9-bf95-74f5eb56d4ad');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '06b8b07f-b49b-476e-adb9-9b814ce25e27');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'be6743b2-0879-40f1-98f9-58fe60492010');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '0705d724-48ce-4376-8940-8653a3b55dd7');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '183a2f1b-b560-45bc-81d5-cc3a1f7e78df');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '0e86acc4-41c7-4aa6-a57f-9587865277e9');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '3643d4ae-1afe-4b3f-bfc1-1741065bc931');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '196c47eb-9e34-4170-bb52-e19a6050e888');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '07c60a41-0271-4c87-b24e-8a2f907bf638');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'd773ecab-aac3-457d-ba8d-88ecd68d276f');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'f54ba5cd-b7d8-499c-b09e-0b04b8ef11da');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'f8c492e4-097f-4573-ae77-c630639dd2c1');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'e6afe944-981e-4cfb-8fe7-a82f2675182d');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '88642a19-087d-40fa-9e02-9a2a2395570c');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '8b685c0c-750b-4039-a18f-9a984fee1a4d');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '7231feb2-9e98-4d81-928c-01cddd6749f8');
insert into t_roles_permissions(role_id, permissions_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'a6e02404-e2db-4882-b754-65c0f4117f7c');


insert into t_roles_permissions(role_id, permissions_id)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', 'a57a580a-18f7-4314-a77c-757d58bcbfa0');
insert into t_roles_permissions(role_id, permissions_id)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', '2ef58c2e-435d-469f-ae25-f6ddaea07c6d');



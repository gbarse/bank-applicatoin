create table t_users
(
  id              uuid primary key                                   not null,
  logged_id       uuid,
  passport_number varchar(25)                                        not null,
  created         timestamp with time zone default current_timestamp not null,
  updated         timestamp with time zone default current_timestamp not null,
  deleted         timestamp with time zone default current_timestamp null
);

create table t_physical_account
(
  id             uuid primary key                                   not null,
  ssn            varchar(3)                                         not null,
  currency       varchar(25),
  status         varchar(25),
  amount         decimal,
  account_number varchar(30),
  user_id        uuid                                               not null,
  created        timestamp with time zone default current_timestamp not null,
  updated        timestamp with time zone default current_timestamp not null,
  deleted        timestamp with time zone default current_timestamp null
);


create table t_legal_account
(
  id             uuid primary key                                   not null,
  user_id        uuid                                               not null,
  status         varchar(25)                                        not null,
  currency       varchar(20)                                        not null,
  company_type   varchar(25)                                        not null,
  amount         decimal,
  ssh            varchar(16)                                        not null,
  account_number varchar(30)                                        not null,
  created        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  updated        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  company_name   varchar(50),
  deleted        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null
);


ALTER TABLE t_legal_account
  ADD CONSTRAINT t_legal_account_fk_one_to_one FOREIGN KEY (user_id) REFERENCES t_users (id);

ALTER TABLE t_physical_account
  ADD CONSTRAINT t_physical_account_fk_one_to_one FOREIGN KEY (user_id) REFERENCES t_users (id);


create table t_physical_credit_card
(
  id                  uuid primary key                                   not null,

  card_level          varchar(20)                                        not null,
  card_type           varchar(20)                                        not null,
  card_number         varchar(16)                                        not null,
  currency            varchar(20)                                        not null,
  amount              decimal,
  status              varchar(20)                                        not null,
  cvv                 varchar(3)                                         not null,
  exp_date            date,
  physical_account_id uuid                                               not null,
  created             TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  updated             TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  deleted             TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null


);

ALTER TABLE t_physical_credit_card
  ADD CONSTRAINT physical_card_fk_one_to_one FOREIGN KEY (physical_account_id) REFERENCES t_physical_account (id);

create table t_legal_credit_card
(
  id               uuid primary key                                   not null,
  card_level       varchar(20)                                        not null,
  card_type        varchar(20)                                        not null,
  card_number      varchar(16)                                        not null,
  currency         varchar(20)                                        not null,
  amount           decimal,
  status           varchar(20)                                        not null,
  exp_date         date                                               not null,
  cvv              varchar(3)                                         not null,
  created          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  updated          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP not null,
  legal_account_id uuid                                               not null,
  deleted          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP null


);

ALTER TABLE t_legal_credit_card
  ADD CONSTRAINT legal_card_fk_one_to_one FOREIGN KEY (legal_account_id) REFERENCES t_legal_account (id);


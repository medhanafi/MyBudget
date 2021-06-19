
alter table user_ add constraint FKbcu8p5b5n8n5kw7olernwnnq0 foreign key (family) references family;

CREATE TABLE oauth_access_token (
  token_id VARCHAR(255),
  TOKEN BYTEA,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BYTEA,
  refresh_token VARCHAR(255)
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(255),
  token BYTEA,
  authentication BYTEA
);

-- pwd: user-T@sk_kmS0ft
INSERT INTO user_
(account_expired, account_locked, credentials_expired, enabled, "password", user_name, family)
VALUES(false, false, false, true, '$2a$04$agEtvQxw54Q1nb5X5x7rl.TVV.0WIFpzZf3k5wrTUVQw/gUGFnUTK', 'tasks_user', 86);

-- pwd: admin-T@sk_kmS0ft
INSERT INTO user_
(account_expired, account_locked, credentials_expired, enabled, "password", user_name, family)
VALUES(false, false, false, true, '$2a$04$osEqPg/qjfAi4Xc95KLsU.lRC0/o9VZ/1WZRUCKYV/D9S1yGyZ8ny',	'tasks_admin', 86);



INSERT INTO authority ("name") VALUES('USER');
INSERT INTO authority ("name") VALUES('ADMIN');

INSERT INTO users_authorities (user_id, authority_id)VALUES(2, 2);
INSERT INTO users_authorities (user_id, authority_id)VALUES(1, 1);



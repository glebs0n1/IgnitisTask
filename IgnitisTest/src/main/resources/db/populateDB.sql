DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
INSERT INTO users (email, password)
VALUES ('FirstUser@gmail.com', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');
INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);
INSERT INTO posts (user_id, title, text)
VALUES (100000, 'User Post_1','User Hello'),
       (100001, 'Admin Post_2','Best admin hey');

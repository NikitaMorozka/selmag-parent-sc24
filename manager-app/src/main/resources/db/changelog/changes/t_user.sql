create table if not exists user_management.t_user
(
    id        serial primary key,
    c_username   varchar not null check (length(trim(c_username)) > 0) unique,
    c_password varchar not null
    );
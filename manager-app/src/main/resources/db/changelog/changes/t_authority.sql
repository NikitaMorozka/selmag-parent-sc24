create table if not exists user_management.t_authority(
    id        serial primary key,
    c_authority   varchar not null check (length(trim(c_authority)) > 0) unique
    );

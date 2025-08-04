create table if not exists user_management.t_user_authority(
                                                               id serial primary key,
                                                               id_user int not null references user_management.t_user(id),
    id_authority int not null references user_management.t_authority(id),
    constraint uk_user_authority unique(id_user, id_authority)
    )
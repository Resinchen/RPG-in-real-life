create table USERS_PERKS (
    user_id int8 not null references USERS,
    perk_id int8 not null references PERKS,
    primary key (user_id, perk_id)
);
create table PERKS (
    perk_id int8 not null,
    charisma int4,
    description varchar(255) not null,
    endurance int4,
    intelligence int4,
    lucky int4,
    name varchar(255) not null,
    strength int4,
    primary key (perk_id)
);

create table USERS (
    user_id int8 not null,
    charisma int4,
    endurance int4,
    experience int4,
    free_points int4,
    intelligence int4,
    strength int4,
    level int4,
    lucky int4,
    name varchar(255)  not null,
    primary key (user_id)
);

create table perks (
    perk_id int8 not null,
    charisma int4,
    perk_description varchar(255) not null,
    endurance int4,
    intelligence int4,
    lucky int4,
    perk_name varchar(255) not null,
    strength int4,
    primary key (perk_id)
);

create table users (
    user_id int8 not null,
    charisma int4,
    endurance int4,
    experience int4,
    free_points int4,
    intelligence int4,
    level int4,
    lucky int4,
    user_name varchar(255)  not null,
    strength int4,
    primary key (user_id)
);

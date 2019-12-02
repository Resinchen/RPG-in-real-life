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
    quest_id int8,
    primary key (user_id)
);

create table QUESTS (
    quest_id int8 not null,
    name varchar(255)  not null,
    description varchar(255) not null,
    type varchar(255) not null,
    answer varchar(255),
    min_level int4,
    experience int4,
    primary key (quest_id)
);
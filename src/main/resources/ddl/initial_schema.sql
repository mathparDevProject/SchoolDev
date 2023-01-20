create table if not exists schools
(
    id       bigint auto_increment
        primary key,
    approved bit          null,
    name     varchar(255) null
);

create table if not exists school_addresses
(
    short_address varchar(255) null,
    school_id     bigint       not null
        primary key,
    constraint address_to_school
        foreign key (school_id) references schools (id) on DELETE cascade
);

create table if not exists user_profiles
(
    profile_id    bigint auto_increment
        primary key,
    account_id    bigint       null,
    creation_date datetime     null,
    profile_name  varchar(255) null,
    roles         varchar(255) null,
    school_id     bigint       null,
    constraint user_to_school
        foreign key (school_id) references schools (id) on delete cascade
);

create table if not exists school_classes
(
    id        bigint auto_increment
        primary key,
    name      varchar(255) null,
    school_id bigint       null,
    constraint class_to_school
        foreign key (school_id) references schools (id) on delete cascade
);

create table if not exists school_groups
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) null,
    school_id  bigint       not null,
    teacher_id bigint       null,
    constraint group_to_school
        foreign key (school_id) references schools (id) on delete cascade,
    constraint group_to_user
        foreign key (teacher_id) references user_profiles (profile_id) on delete set null
);

create table if not exists group_student
(
    group_id   bigint not null,
    student_id bigint not null,
    constraint user_group_to_user
        foreign key (student_id) references user_profiles (profile_id) on delete cascade,
    constraint user_group_to_group
        foreign key (group_id) references school_groups (id) on delete cascade
);


create table if not exists class_student
(
    class_id   bigint not null,
    student_id bigint not null,
    constraint user_class_to_school
        foreign key (class_id) references school_classes (id) on delete cascade,
    constraint user_class_to_user
        foreign key (student_id) references user_profiles (profile_id) on delete cascade
);

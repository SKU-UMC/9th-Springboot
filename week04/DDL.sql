create table food
(
    id   bigint auto_increment
        primary key,
    name enum ('HanSik') null
);

create table location
(
    id   bigint auto_increment
        primary key,
    name varchar(255) not null
);

create table member
(
    id             bigint auto_increment
        primary key,
    created_at     datetime(6)                                                                                                                                                                                                                                                                                            not null,
    updated_at     datetime(6)                                                                                                                                                                                                                                                                                            null,
    birth          date                                                                                                                                                                                                                                                                                                   not null,
    email          varchar(255)                                                                                                                                                                                                                                                                                           null,
    gender         enum ('FEMALE', 'MALE', 'NONE')                                                                                                                                                                                                                                                                        not null,
    name           varchar(3)                                                                                                                                                                                                                                                                                             not null,
    password       varchar(255)                                                                                                                                                                                                                                                                                           null,
    address        enum ('DOBONG', 'DONGDAEMUN', 'DONGJAK', 'EUNPYEONG', 'GANGBUK', 'GANGDONG', 'GANGNAM', 'GANGSEO', 'GEUMCHEON', 'GURO', 'GWANAK', 'GWANGJIN', 'JONGNO', 'JUNG', 'JUNGNANG', 'MAPO', 'NONE', 'NOWON', 'SEOCHO', 'SEODAEMUN', 'SEONGBUK', 'SEONGDONG', 'SONGPA', 'YANGCHEON', 'YEONGDEUNGPO', 'YONGSAN') not null,
    delete_at      datetime(6)                                                                                                                                                                                                                                                                                            null,
    detail_address varchar(255)                                                                                                                                                                                                                                                                                           not null,
    phone_number   varchar(255)                                                                                                                                                                                                                                                                                           null,
    point          int                                                                                                                                                                                                                                                                                                    not null
);

create table member_food
(
    id        bigint auto_increment
        primary key,
    food_id   bigint null,
    member_id bigint null,
    constraint FK5i0iwac2dcdifkxtb64cd17o8
        foreign key (member_id) references member (id),
    constraint FKj1eo2o1lys37eeqycfahfr0h9
        foreign key (food_id) references food (id)
);

create table reply
(
    id        bigint auto_increment
        primary key,
    content   tinytext not null,
    review_id bigint   null,
    constraint UK4uh4vegunuxj6p9ilmeyy6gm2
        unique (review_id)
);

create table store
(
    id             bigint auto_increment
        primary key,
    detail_address varchar(255) not null,
    manager_number bigint       not null,
    name           varchar(255) not null,
    location_id    bigint       null,
    constraint FKh9ktaju7lqhxawiucwdxkmn46
        foreign key (location_id) references location (id)
);

create table mission
(
    id                bigint auto_increment
        primary key,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  null,
    deadline          date         not null,
    mission_condition varchar(255) not null,
    point             int          not null,
    store_id          bigint       null,
    constraint FKckx1b8plp95qtdk73kylhy12n
        foreign key (store_id) references store (id)
);

create table member_mission
(
    id           bigint auto_increment
        primary key,
    is_completed bit    not null,
    member_id    bigint null,
    mission_id   bigint null,
    constraint FKibnub11mc8k2g39v44smt9jqi
        foreign key (mission_id) references mission (id),
    constraint FKpgw7uojmq1tkna2ubpxmhlyuo
        foreign key (member_id) references member (id)
);

create table review
(
    id         bigint auto_increment
        primary key,
    reply_id   bigint      null,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    content    tinytext    not null,
    star       float       not null,
    member_id  bigint      null,
    store_id   bigint      null,
    constraint UKeumwu8ibnc3l3724nvintt2nl
        unique (reply_id),
    constraint FK74d12ba8sxxu9vpnc59b43y30
        foreign key (store_id) references store (id),
    constraint FKdpfcddw0hlg2rqdskg3jerdy7
        foreign key (reply_id) references reply (id),
    constraint FKk0ccx5i4ci2wd70vegug074w1
        foreign key (member_id) references member (id)
);

alter table reply
    add constraint FKd5ckwt38d4ibe84wlfc3o8jw8
        foreign key (review_id) references review (id);

create table term
(
    id   bigint auto_increment
        primary key,
    name enum ('tmp1') null
);

create table member_term
(
    id        bigint auto_increment
        primary key,
    member_id bigint null,
    term_id   bigint null,
    constraint FK99valiqden00uing9dy6221uy
        foreign key (term_id) references term (id),
    constraint FKrtih56dvkore774vnao4lglic
        foreign key (member_id) references member (id)
);


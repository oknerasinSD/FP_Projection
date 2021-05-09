create sequence hibernate_sequence start 1 increment 1;

create table usr (
    id bigserial not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

create table user_role (
                           user_id bigserial not null,
                           roles varchar(255),
                           foreign key (user_id) references usr (id)
);

create table tournament (
                            id bigserial not null,
                            title varchar(255),
                            league varchar(25),
                            startDate date,
                            startTime time,
                            endDate date,
                            endTime time,
                            filename varchar(255),
                            primary key (id)
);

create table player_forecast(
                                id bigserial not null,
                                system_tournament_id int8 not null,
                                fanteam_tournament_id int8,
                                fanteam_player_id int8,
                                player_name varchar(255),
                                team varchar(25),
                                position varchar(25),
                                expected_points double precision,
                                price double precision,
                                PRIMARY KEY (id),
                                FOREIGN KEY (system_tournament_id) REFERENCES tournament (id)
);

CREATE TABLE tournament_team
(
    id bigserial NOT NULL,
    tournament_id int8 NOT NULL,
    team varchar(25),
    PRIMARY KEY (id),
    FOREIGN KEY (tournament_id) REFERENCES tournament (id)
);
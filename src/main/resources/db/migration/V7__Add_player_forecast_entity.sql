create table player_forecast(
    id int8 not null,
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
)
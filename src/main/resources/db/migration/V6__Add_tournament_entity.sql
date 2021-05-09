create table tournament (
    id int8 not null,
    title varchar(255),
    league varchar(25),
    startDate date,
    startTime time,
    endDate date,
    endTime time,
    primary key (id)
)
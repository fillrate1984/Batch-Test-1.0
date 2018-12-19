create table if not exists person
(
	id int not null unique,
    firstName varchar(100),
    lastName varchar(100),
    birthDay Date
);

create table if not exists customer
(
	id int not null unique,
    firstName varchar(100),
    lastName varchar(100),
    birthDay Date
);


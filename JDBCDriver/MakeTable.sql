USE CSCI201;

CREATE TABLE Account (
  userID int(11) primary key not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null,
  status varchar(50) not null,
  hours_worked int(11),
  hourly_rate_of_pay int(11)
);

CREATE TABLE Timeslot (
	timeslotID int(11) primary key not null auto_increment,
    userID int(11) not null,
    day varchar(50) not null,
    clock int(11) not null,
    status varchar(50) not null
);

CREATE TABLE Colors (
	ColorID int(11) primary key not null auto_increment,
    color varchar(50)
);

INSERT INTO Colors (color) VALUE ('RED');
INSERT INTO Colors (color) VALUE ('GREEN');
INSERT INTO Colors (color) VALUE ('BLUE');
INSERT INTO Colors (color) VALUE ('YELLOW');
INSERT INTO Colors (color) VALUE ('PINK');
INSERT INTO Colors (color) VALUE ('BLACK');

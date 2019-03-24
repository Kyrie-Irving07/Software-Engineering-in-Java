DROP PROCEDURE if exists Assignment3;

CREATE DATABASE Assignment3;

USE Assignment3;

CREATE TABLE User (
  userID int(11) primary key not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null
);

CREATE TABLE History (
  historyID int(11) primary key not null auto_increment,
  history varchar(50) not null,
  username varchar(50) not null
);

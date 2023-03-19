DROP DATABASE IF EXISTS miniproject2;

CREATE DATABASE miniproject2;

USE miniproject2;

CREATE TABLE users (
  firstName VARCHAR(128) NOT NULL,
  lastName VARCHAR(128) NOT NULL,
  email VARCHAR(128) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  role ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
  verified TINYINT NOT NULL DEFAULT 0,
  verificationString VARCHAR(64) UNIQUE,
  resetPwdString VARCHAR(64) UNIQUE,

  PRIMARY KEY(email)
);
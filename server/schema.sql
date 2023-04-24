DROP DATABASE IF EXISTS miniproject2;

CREATE DATABASE miniproject2;

USE miniproject2;

CREATE TABLE users (
	id VARCHAR(36) NOT NULL,
	first_name VARCHAR(128) NOT NULL,
	last_name VARCHAR(128) NOT NULL,
	email VARCHAR(128) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL,
	role ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
	verified TINYINT NOT NULL DEFAULT 0,
	verification_string VARCHAR(64) UNIQUE,
	reset_pwd_string VARCHAR(64) UNIQUE,

	PRIMARY KEY(id)
);

CREATE TABLE owners (
	name VARCHAR(128),
	logo_symbol_url VARCHAR(256),
	logo_lockup_url VARCHAR(256),
	id VARCHAR(128),
	iata_code VARCHAR(128),
	conditions_of_carriage_url VARCHAR(256),
  
	PRIMARY KEY(id)
);

CREATE TABLE orders (
	total_currency VARCHAR(128),
	total_amount VARCHAR(128),
	tax_currency VARCHAR(128),
	tax_amount VARCHAR(128),
	owners_id VARCHAR(128),
	id VARCHAR(128),
	created_at VARCHAR(128),
	booking_reference VARCHAR(128),
	base_currency VARCHAR(128),
	base_amount VARCHAR(128),
	users_id VARCHAR(36),
  
	PRIMARY KEY(id),
	CONSTRAINT fk_users_id_orders FOREIGN KEY(users_id) REFERENCES users(id)
    ON DELETE SET NULL,
	CONSTRAINT fk_owners_id_orders FOREIGN KEY(owners_id) REFERENCES owners(id)
    ON DELETE SET NULL
);

CREATE TABLE slices (
	origin_name VARCHAR(128),
	origin_iata_country_code VARCHAR(128),
	origin_iata_code VARCHAR(128),
	id VARCHAR(128),
	fare_brand_name VARCHAR(128),
	duration VARCHAR(128),
	destination_name VARCHAR(128),
	destination_iata_country_code VARCHAR(128),
	destination_iata_code VARCHAR(128),
	orders_id VARCHAR(128),
  
	PRIMARY KEY(id),
	CONSTRAINT fk_orders_id_slices FOREIGN KEY(orders_id) REFERENCES orders(id)
    ON DELETE CASCADE
);

CREATE TABLE operating_carrier (
	name VARCHAR(128),
	logo_symbol_url VARCHAR(256),
	logo_lockup_url VARCHAR(256),
	id VARCHAR(128),
	iata_code VARCHAR(128),
	conditions_of_carriage_url VARCHAR(256),
  
	PRIMARY KEY(id)
);

CREATE TABLE marketing_carrier (
	name VARCHAR(128),
	logo_symbol_url VARCHAR(256),
	logo_lockup_url VARCHAR(256),
	id VARCHAR(128),
	iata_code VARCHAR(128),
	conditions_of_carriage_url VARCHAR(256),
	
	PRIMARY KEY(id)
);

CREATE TABLE aircraft (
	name VARCHAR(128),
	id VARCHAR(128),
	iata_code VARCHAR(128),
  
	PRIMARY KEY(id)
);

CREATE TABLE segments (
	origin_terminal VARCHAR(128),
	origin_name VARCHAR(128),
	origin_iata_country_code VARCHAR(128),
	origin_iata_code VARCHAR(128),
	operating_carrier_flight_number VARCHAR(128),
	operating_carrier_id VARCHAR(128),
	marketing_carrier_flight_number VARCHAR(128),
	marketing_carrier_id VARCHAR(128),
	id VARCHAR(128),
    duration VARCHAR(128),
	distance VARCHAR(128),
	destination_terminal VARCHAR(128),
	destination_name VARCHAR(128),
	destination_iata_country_code VARCHAR(128),
	destination_iata_code VARCHAR(128),
	departing_at VARCHAR(128),
	arriving_at VARCHAR(128),
	aircraft_id VARCHAR(128),
	slices_id VARCHAR(128),
  
	PRIMARY KEY(id),
	CONSTRAINT fk_slices_id_segments FOREIGN KEY(slices_id) REFERENCES slices(id) ON DELETE CASCADE,
	CONSTRAINT fk_operating_carrier_id_segments FOREIGN KEY(operating_carrier_id) 
    REFERENCES operating_carrier(id) ON DELETE SET NULL,
	CONSTRAINT fk_marketing_carrier_id_segments FOREIGN KEY(marketing_carrier_id) 
    REFERENCES marketing_carrier(id) ON DELETE SET NULL,
	CONSTRAINT fk_aircraft_id_segments FOREIGN KEY(aircraft_id) REFERENCES aircraft(id)
);

CREATE TABLE segment_passengers (
	passenger_id VARCHAR(128),
	cabin_class_marketing_name VARCHAR(128),
	cabin_class VARCHAR(128),
	segments_id VARCHAR(128),
  
	PRIMARY KEY(passenger_id),
	CONSTRAINT fk_segments_id_segment_passengers FOREIGN KEY(segments_id) 
    REFERENCES segments(id)
    ON DELETE CASCADE
);

CREATE TABLE baggages (
	id INT AUTO_INCREMENT,
	type VARCHAR(128),
	quantity INT,
	segment_passengers_id VARCHAR(128),
  
	PRIMARY KEY(id),
	CONSTRAINT fk_segments_passengers_id_baggages FOREIGN KEY(segment_passengers_id) 
    REFERENCES segment_passengers(passenger_id)
    ON DELETE CASCADE
);

CREATE TABLE passengers (
	type VARCHAR(128),
	title VARCHAR(128),
	phone_number VARCHAR(128),
	id VARCHAR(128),
	given_name VARCHAR(128),
	gender VARCHAR(128),
	family_name VARCHAR(128),
	email VARCHAR(128),
	born_on VARCHAR(128),
	orders_id VARCHAR(128),
  
	PRIMARY KEY(id),
	CONSTRAINT fk_orders_id_passengers FOREIGN KEY(orders_id) REFERENCES orders(id)
    ON DELETE CASCADE
);

CREATE TABLE conditions (
	id INT AUTO_INCREMENT,
	orders_id VARCHAR(128) UNIQUE,
  
	PRIMARY KEY(id),
	CONSTRAINT fk_orders_id_conditions FOREIGN KEY(orders_id) REFERENCES orders(id)
    ON DELETE CASCADE
);

CREATE TABLE refund_before_departure (
	id INT AUTO_INCREMENT,
	penalty_currency VARCHAR(128),
	penalty_amount VARCHAR(128),
	allowed TINYINT,
	conditions_id INT UNIQUE,
  
	PRIMARY KEY(id),
	CONSTRAINT fk_conditions_id_refund_before_departure FOREIGN KEY(conditions_id) 
    REFERENCES conditions(id)
    ON DELETE CASCADE
);

CREATE TABLE change_before_departure (
	id INT AUTO_INCREMENT,
	penalty_currency VARCHAR(128),
	penalty_amount VARCHAR(128),
	allowed TINYINT,
	conditions_id INT UNIQUE,
  
	PRIMARY KEY(id),
	CONSTRAINT fk_conditions_id_change_before_departure FOREIGN KEY(conditions_id) 
    REFERENCES conditions(id)
    ON DELETE CASCADE
);
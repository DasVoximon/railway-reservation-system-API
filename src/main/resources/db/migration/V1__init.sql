CREATE SEQUENCE IF NOT EXISTS admin_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS passenger_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS reservation_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS routes_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS schedule_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS stations_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS trains_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE admins
(
    admin_id     BIGINT NOT NULL,
    email        VARCHAR(255),
    phone_number VARCHAR(255),
    password     VARCHAR(100),
    role         VARCHAR(255),
    first_name   VARCHAR(100),
    middle_name  VARCHAR(100),
    last_name    VARCHAR(100),
    CONSTRAINT pk_admins PRIMARY KEY (admin_id)
);

CREATE TABLE passengers
(
    passenger_id BIGINT NOT NULL,
    email        VARCHAR(255),
    phone_number VARCHAR(255),
    password     VARCHAR(100),
    role         VARCHAR(255),
    first_name   VARCHAR(100),
    middle_name  VARCHAR(100),
    last_name    VARCHAR(100),
    CONSTRAINT pk_passengers PRIMARY KEY (passenger_id)
);

CREATE TABLE reservations
(
    reservation_id     BIGINT       NOT NULL,
    schedule_id        BIGINT,
    passenger_id       BIGINT,
    booked_at          time WITHOUT TIME ZONE,
    reservation_status VARCHAR(255),
    seat_number        VARCHAR(255),
    pnr                VARCHAR(255) NOT NULL,
    CONSTRAINT pk_reservations PRIMARY KEY (reservation_id)
);

CREATE TABLE routes
(
    route_id               BIGINT         NOT NULL,
    train_id               BIGINT,
    origin_station_id      BIGINT,
    destination_station_id BIGINT,
    distance_in_km         DECIMAL(12, 2) NOT NULL,
    CONSTRAINT pk_routes PRIMARY KEY (route_id)
);

CREATE TABLE schedules
(
    schedule_id    BIGINT                 NOT NULL,
    route_id       BIGINT,
    arrival_time   time WITHOUT TIME ZONE NOT NULL,
    departure_time time WITHOUT TIME ZONE NOT NULL,
    travel_date    date                   NOT NULL,
    base_fare      DECIMAL(12, 2)         NOT NULL,
    seats          INTEGER                NOT NULL,
    CONSTRAINT pk_schedules PRIMARY KEY (schedule_id)
);

CREATE TABLE stations
(
    station_id   BIGINT       NOT NULL,
    code         VARCHAR(255) NOT NULL,
    station_name VARCHAR(255),
    city         VARCHAR(255),
    state        VARCHAR(255),
    CONSTRAINT pk_stations PRIMARY KEY (station_id)
);

CREATE TABLE trains
(
    train_id   BIGINT       NOT NULL,
    code       VARCHAR(255) NOT NULL,
    train_name VARCHAR(255),
    capacity   INTEGER      NOT NULL,
    CONSTRAINT pk_trains PRIMARY KEY (train_id)
);

ALTER TABLE passengers
    ADD CONSTRAINT uc_acc48bf7a7d7dc2a1c20d1773 UNIQUE (email);

ALTER TABLE reservations
    ADD CONSTRAINT uc_reservations_pnr UNIQUE (pnr);

ALTER TABLE reservations
    ADD CONSTRAINT uc_reservations_seatnumber UNIQUE (seat_number);

ALTER TABLE stations
    ADD CONSTRAINT uc_stations_code UNIQUE (code);

ALTER TABLE trains
    ADD CONSTRAINT uc_trains_code UNIQUE (code);

ALTER TABLE reservations
    ADD CONSTRAINT FK_RESERVATIONS_ON_PASSENGER FOREIGN KEY (passenger_id) REFERENCES passengers (passenger_id);

ALTER TABLE reservations
    ADD CONSTRAINT FK_RESERVATIONS_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES schedules (schedule_id);

ALTER TABLE routes
    ADD CONSTRAINT FK_ROUTES_ON_DESTINATION_STATION FOREIGN KEY (destination_station_id) REFERENCES stations (station_id);

ALTER TABLE routes
    ADD CONSTRAINT FK_ROUTES_ON_ORIGIN_STATION FOREIGN KEY (origin_station_id) REFERENCES stations (station_id);

ALTER TABLE routes
    ADD CONSTRAINT FK_ROUTES_ON_TRAIN FOREIGN KEY (train_id) REFERENCES trains (train_id);

ALTER TABLE schedules
    ADD CONSTRAINT FK_SCHEDULES_ON_ROUTE FOREIGN KEY (route_id) REFERENCES routes (route_id);
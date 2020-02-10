--
-- File generated with SQLiteStudio v3.2.1 on pon feb 10 22:46:16 2020
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: auditorium
DROP TABLE IF EXISTS auditorium;

CREATE TABLE auditorium (
    id   INTEGER      PRIMARY KEY,
    name VARCHAR (20) NOT NULL
);

INSERT INTO auditorium (id, name) VALUES (1, 'sala1');
INSERT INTO auditorium (id, name) VALUES (2, 'sala2');
INSERT INTO auditorium (id, name) VALUES (3, 'sala3');
INSERT INTO auditorium (id, name) VALUES (4, 'sala4');

-- Table: audScrType
DROP TABLE IF EXISTS audScrType;

CREATE TABLE audScrType (
    audScrTypeId INTEGER PRIMARY KEY,
    auditoriumId INTEGER NOT NULL,
    screenTypeId INTEGER NOT NULL,
    FOREIGN KEY (
        auditoriumId
    )
    REFERENCES auditorium (id) ON DELETE RESTRICT,
    FOREIGN KEY (
        screenTypeId
    )
    REFERENCES screenType (id) ON DELETE RESTRICT
);

INSERT INTO audScrType (audScrTypeId, auditoriumId, screenTypeId) VALUES (1, 1, 1);
INSERT INTO audScrType (audScrTypeId, auditoriumId, screenTypeId) VALUES (2, 1, 2);
INSERT INTO audScrType (audScrTypeId, auditoriumId, screenTypeId) VALUES (3, 2, 1);
INSERT INTO audScrType (audScrTypeId, auditoriumId, screenTypeId) VALUES (4, 2, 3);
INSERT INTO audScrType (audScrTypeId, auditoriumId, screenTypeId) VALUES (5, 3, 2);
INSERT INTO audScrType (audScrTypeId, auditoriumId, screenTypeId) VALUES (6, 4, 3);

-- Table: movie
DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
    id          INTEGER       PRIMARY KEY,
    title       VARCHAR (100) NOT NULL,
    directors   VARCHAR (256) NOT NULL,
    actors      VARCHAR (256) NOT NULL,
    genre       VARCHAR (150) NOT NULL,
    duration    INTEGER       NOT NULL,
    distributor VARCHAR (150) NOT NULL,
    country     VARCHAR (60)  NOT NULL,
    year        INTEGER       NOT NULL,
    description VARCHAR (512) NOT NULL,
    deleted     INTEGER       NOT NULL
);

INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (1, 'Filmm 1', 'Nela i Sandra', 'Milos i Radovan', 'Horor', 120, 'Blitz', 'Serbia', 2016, 'description1', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (2, 'Film 2', 'Petar', 'Milan', 'Comedy', 90, 'Blitz', 'Macedonia', 2019, 'description 2', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (3, 'Movie 3', 'Mika', 'Zika', 'Drama', 90, 'Blitz', 'Serbia', 2018, 'description 3', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (5, 'Limittless', 'Radovan', 'Nela', 'Comedy', 30, 'Blitz', 'Russia', 2019, 'description 5', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (6, 'Film 6', 'Sandra', 'Radovan', 'Tragicomedy', 60, 'Blitz', 'Serbia', 2020, 'description 6', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (7, 'Film 7 ', 'Zoran', 'Ervin', 'Fantasy', 75, 'Blitz', 'Hungary', 2012, 'description 7', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (8, 'Film 8', 'Gari', 'Djole', 'Horor', 30, 'Blitz', 'Serbia', 2019, 'description 8', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (9, 'Film 9 ', 'Bernard', 'Nesa', 'Romantic', 90, 'Blitz', 'Serbia', 2020, 'description 9', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (10, 'Film 10 ', 'Jesa', 'Vuk', 'Anime', 90, 'Blitz', 'China', 2019, 'description 10', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (11, 'Me before you', 'Nela i Sandra', 'Sandra', 'drama', 45, 'Blitz', 'Serbia', 2020, 'desc', 0);
INSERT INTO movie (id, title, directors, actors, genre, duration, distributor, country, year, description, deleted) VALUES (12, 'd', 'd', 'd', 'd', 2, 'd', 'Serbia', 2020, 'd', 1);

-- Table: screening
DROP TABLE IF EXISTS screening;

CREATE TABLE screening (
    id           INTEGER      PRIMARY KEY,
    movieId      INTEGER      NOT NULL,
    screenTypeId INTEGER      NOT NULL,
    auditoriumId INTEGER      NOT NULL,
    datetime     INTEGER      NOT NULL,
    ticketPrice  REAL         NOT NULL,
    user         VARCHAR (10) NOT NULL,
    deleted      INTEGER      NOT NULL,
    FOREIGN KEY (
        movieId
    )
    REFERENCES movie (id) ON DELETE RESTRICT,
    FOREIGN KEY (
        auditoriumId
    )
    REFERENCES auditorium (id) ON DELETE RESTRICT,
    FOREIGN KEY (
        screenTypeId
    )
    REFERENCES screenType (id) ON DELETE RESTRICT,
    FOREIGN KEY (
        user
    )
    REFERENCES user (username) ON DELETE RESTRICT
);

INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (3, 8, 1, 2, 1581264000000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (4, 8, 1, 2, 1581267600000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (7, 5, 2, 3, 1581256800000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (8, 5, 2, 3, 1581264000000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (9, 6, 1, 1, 1581267600000, 350.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (10, 6, 3, 4, 1581274800000, 350.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (11, 1, 2, 3, 1581246000000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (12, 1, 3, 4, 1581256800000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (13, 2, 1, 1, 1581354000000, 300.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (14, 2, 1, 2, 1581350400000, 300.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (15, 3, 2, 3, 1581357600000, 250.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (16, 3, 3, 4, 1581364800000, 250.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (19, 9, 2, 3, 1581368400000, 100.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (20, 9, 1, 2, 1581350400000, 100.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (21, 10, 2, 1, 1581368400000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (22, 10, 2, 1, 1581361200000, 100.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (23, 1, 1, 1, 1581422400000, 450.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (24, 1, 1, 2, 1581433200000, 450.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (25, 2, 1, 2, 1581422400000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (26, 2, 3, 4, 1581433200000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (27, 6, 2, 3, 1581447600000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (28, 6, 1, 2, 1581451200000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (29, 5, 2, 1, 1581436800000, 400.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (30, 5, 1, 1, 1581440400000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (31, 7, 2, 1, 1581447600000, 250.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (32, 7, 3, 4, 1581451200000, 250.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (33, 6, 1, 1, 1581526800000, 350.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (34, 6, 3, 4, 1581534000000, 350.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (35, 8, 1, 2, 1581523200000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (36, 8, 1, 2, 1581526800000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (39, 5, 2, 3, 1581516000000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (40, 5, 2, 3, 1581523200000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (41, 1, 2, 3, 1581505200000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (42, 1, 3, 4, 1581516000000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (43, 2, 1, 1, 1581591600000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (44, 2, 1, 2, 1581595200000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (45, 2, 1, 2, 1581595200000, 150.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (46, 3, 2, 3, 1581609600000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (47, 3, 3, 4, 1581616800000, 200.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (48, 8, 3, 2, 1581616800000, 300.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (49, 8, 3, 2, 1581620400000, 300.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (50, 10, 2, 3, 1581624000000, 350.0, 'aa', 0);
INSERT INTO screening (id, movieId, screenTypeId, auditoriumId, datetime, ticketPrice, user, deleted) VALUES (51, 10, 2, 1, 1581627600000, 350.0, 'aa', 0);

-- Table: screenType
DROP TABLE IF EXISTS screenType;

CREATE TABLE screenType (
    id   INTEGER     PRIMARY KEY,
    name VARCHAR (5) NOT NULL
);

INSERT INTO screenType (id, name) VALUES (1, '2D');
INSERT INTO screenType (id, name) VALUES (2, '3D');
INSERT INTO screenType (id, name) VALUES (3, '4D');

-- Table: seat
DROP TABLE IF EXISTS seat;

CREATE TABLE seat (
    id           INTEGER PRIMARY KEY,
    seatNO       INTEGER NOT NULL,
    auditoriumId INTEGER NOT NULL,
    FOREIGN KEY (
        auditoriumId
    )
    REFERENCES auditorium (id) ON DELETE RESTRICT
);

INSERT INTO seat (id, seatNO, auditoriumId) VALUES (1, 1, 1);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (2, 2, 1);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (3, 3, 1);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (4, 4, 1);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (5, 5, 1);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (6, 1, 2);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (7, 2, 2);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (8, 3, 2);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (9, 4, 2);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (10, 5, 2);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (11, 1, 3);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (12, 2, 3);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (13, 3, 3);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (14, 4, 3);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (15, 5, 3);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (16, 1, 4);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (17, 2, 4);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (18, 3, 4);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (19, 4, 4);
INSERT INTO seat (id, seatNO, auditoriumId) VALUES (20, 5, 4);

-- Table: ticket
DROP TABLE IF EXISTS ticket;

CREATE TABLE ticket (
    id          INTEGER      PRIMARY KEY,
    screeningId INTEGER      NOT NULL,
    seatId      INTEGER      NOT NULL,
    datetime    INTEGER      NOT NULL,
    user        VARCHAR (10) NOT NULL,
    deleted     INTEGER      NOT NULL,
    FOREIGN KEY (
        screeningId
    )
    REFERENCES screening (id) ON DELETE RESTRICT,
    FOREIGN KEY (
        seatId
    )
    REFERENCES seat (id) ON DELETE RESTRICT,
    FOREIGN KEY (
        user
    )
    REFERENCES user (username) ON DELETE RESTRICT
);

INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (1, 21, 1, 1581367080131, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (2, 21, 2, 1581367080132, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (3, 21, 4, 1581367093049, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (4, 50, 11, 1581367110794, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (5, 51, 3, 1581367116739, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (6, 50, 14, 1581367124925, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (7, 50, 13, 1581367145584, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (8, 26, 16, 1581369449288, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (9, 25, 6, 1581369603478, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (10, 25, 7, 1581369603479, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (11, 43, 4, 1581369617278, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (12, 44, 7, 1581369623862, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (13, 25, 9, 1581369685597, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (14, 26, 20, 1581369693397, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (15, 44, 10, 1581369702285, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (16, 45, 8, 1581369709133, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (17, 45, 9, 1581369709134, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (18, 27, 12, 1581369725429, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (19, 27, 13, 1581369725430, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (20, 33, 4, 1581369731709, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (21, 34, 19, 1581369737637, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (22, 31, 3, 1581369752524, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (23, 32, 18, 1581369758100, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (24, 35, 6, 1581370720917, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (25, 35, 7, 1581370720918, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (26, 36, 8, 1581370729404, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (27, 48, 9, 1581370735563, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (28, 48, 10, 1581370735563, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (29, 49, 8, 1581370742138, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (30, 23, 3, 1581370785028, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (31, 24, 8, 1581370791699, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (32, 24, 9, 1581370791700, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (33, 41, 13, 1581370799154, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (34, 42, 18, 1581370807307, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (35, 29, 2, 1581370820522, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (36, 30, 2, 1581370826010, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (37, 30, 3, 1581370826011, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (38, 39, 14, 1581370832179, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (39, 40, 12, 1581370837858, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (40, 46, 15, 1581370849418, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (41, 47, 17, 1581370855666, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (42, 47, 18, 1581370855667, 'b', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (43, 27, 14, 1581370915747, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (44, 28, 8, 1581370922162, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (45, 28, 9, 1581370922162, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (46, 33, 2, 1581370930706, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (47, 35, 10, 1581370941850, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (48, 49, 7, 1581370949898, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (49, 24, 7, 1581370987875, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (50, 30, 4, 1581371001642, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (51, 30, 5, 1581371001643, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (52, 39, 13, 1581371012995, 's', 0);
INSERT INTO ticket (id, screeningId, seatId, datetime, user, deleted) VALUES (53, 46, 13, 1581371025026, 's', 0);

-- Table: user
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    username VARCHAR (10) NOT NULL,
    password VARCHAR (10) NOT NULL,
    datetime INTEGE       NOT NULL,
    role     VARCHAR (5)  NOT NULL
                          DEFAULT 'USER',
    deleted  INTEGER      NOT NULL,
    PRIMARY KEY (
        username
    )
);

INSERT INTO user (username, password, datetime, role, deleted) VALUES ('aa', 'a', 1580754995535, 'ADMIN', 0);
INSERT INTO user (username, password, datetime, role, deleted) VALUES ('b', 'b', 1581345165487, 'USER', 0);
INSERT INTO user (username, password, datetime, role, deleted) VALUES ('s', 's', 1581345178763, 'USER', 0);
INSERT INTO user (username, password, datetime, role, deleted) VALUES ('m', 'm', 1581359207827, 'USER', 1);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

-- DROP DATABASE IF EXISTS MonHunRise;

DROP TYPE IF EXISTS payment_type_t CASCADE;
DROP TYPE IF EXISTS level_t CASCADE ;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS leading_players CASCADE;
DROP TABLE IF EXISTS regular_players CASCADE;
DROP TABLE IF EXISTS leading_hunters CASCADE;
DROP TABLE IF EXISTS hunters_guilds CASCADE;
DROP TABLE IF EXISTS owned_hunters CASCADE;
DROP TABLE IF EXISTS sponsored_hunts CASCADE;
DROP TABLE IF EXISTS ongoing_hunts;


-- CREATE DATABASE MonHunRise;

CREATE TYPE payment_type_t AS ENUM ('zennies (gold)', 'coins', 'tickets');
CREATE TYPE level_t AS ENUM ('low rank', 'high rank', 'god rank');

CREATE TABLE locations
(
    id          INT PRIMARY KEY,
    country     VARCHAR(200) NOT NULL,
    field       VARCHAR(200) NOT NULL
);

CREATE TABLE leading_players
(
    id              INT PRIMARY KEY,
    nickname        VARCHAR(100) NOT NULL,
    license_num     INT          NOT NULL
);

CREATE TABLE regular_players
(
    id                  INT PRIMARY KEY,
    nickname            VARCHAR(100) NOT NULL,
    auth_token          TEXT         NOT NULL,
    home_location_id    INT REFERENCES locations (id),
    hunt_location_id    INT REFERENCES locations (id)
);

CREATE TABLE leading_hunters
(
    id          INT PRIMARY KEY,
    class       VARCHAR(50)   NOT NULL,
    level       level_t       NOT NULL,
    capacity    NUMERIC(2, 0) NOT NULL,
    HR          VARCHAR(15)   NOT NULL
);

CREATE TABLE hunters_guilds
(
    id      INT PRIMARY KEY,
    name    VARCHAR(70) NOT NULL,
    palico  VARCHAR(90) NOT NULL
);

CREATE TABLE owned_hunters
(
    hunter_id       INT PRIMARY KEY REFERENCES leading_hunters (id),
    guild_id        INT NOT NULL REFERENCES hunters_guilds (id),
    rent_price      INT NOT NULL
);

CREATE TABLE sponsored_hunts
(
    id                      INT PRIMARY KEY,
    src_id                  INT            NOT NULL REFERENCES locations (id),
    dst_id                  INT            NOT NULL REFERENCES locations (id),
    start_time              TIMESTAMP      NOT NULL,
    end_time                TIMESTAMP      NOT NULL,
    payment_type            payment_type_t NOT NULL,
    price                   INT            NOT NULL,
    level                   level_t        NOT NULL,
    party_cnt               NUMERIC(2, 0)  NOT NULL,
    leading_player_id       INT            NOT NULL REFERENCES leading_players (id),
    leading_hunter_id       INT            NOT NULL REFERENCES leading_hunters (id),
    regular_player_id       INT            NOT NULL REFERENCES regular_players (id),
    distance                INT            NOT NULL
);

CREATE TABLE ongoing_hunts
(
    src_id                  INT           NOT NULL REFERENCES locations (id),
    dst_id                  INT           NOT NULL REFERENCES locations (id),
    start_time              TIMESTAMP,
    level                   level_t       NOT NULL,
    party_cnt               NUMERIC(2, 0) NOT NULL,
    regular_player_id       INT           NOT NULL REFERENCES regular_players (id),
    leading_hunter_id       INT UNIQUE    NOT NULL REFERENCES leading_hunters (id),
    leading_player_id       INT PRIMARY KEY REFERENCES leading_players (id)
);


CREATE EXTENSION btree_gist;

-- Can't end hunt before it started
ALTER TABLE sponsored_hunts
    ADD CHECK ( start_time < end_time );

-- One leading hunter can lead only one hunt at the same time
ALTER TABLE sponsored_hunts
    ADD EXCLUDE USING gist (
        leading_hunter_id WITH =,
        tsrange(start_time, end_time, '[]') WITH &&
        );

-- One leading player can lead only one hunt at the same time
ALTER TABLE sponsored_hunts
    ADD EXCLUDE USING gist (
        leading_player_id WITH =,
        tsrange(start_time, end_time, '[]') WITH &&
        );

-- Party size (leading hunter capacity) must be big enough to fit all regular_players
CREATE OR REPLACE FUNCTION check_party() RETURNS TRIGGER
AS
$check_party$
BEGIN
    IF NOT exists(
            SELECT *
            FROM leading_hunters
            WHERE leading_hunters.id = NEW.leading_hunter_id
              AND capacity >= NEW.party_cnt
              AND leading_hunters.level >= NEW.level
        ) THEN
        RAISE EXCEPTION 'Non-suitable car';
    ELSE
        RETURN NEW;
    END IF;
END;
$check_party$ LANGUAGE plpgsql;

CREATE TRIGGER check_party_complete
    BEFORE INSERT OR UPDATE
    ON sponsored_hunts
    FOR EACH ROW
EXECUTE PROCEDURE check_party();

CREATE TRIGGER check_party_ongoing
    BEFORE INSERT OR UPDATE
    ON ongoing_hunts
    FOR EACH ROW
EXECUTE PROCEDURE check_party();

-- Ongoing hunt can't be started after current
CREATE OR REPLACE FUNCTION check_ongoing_hunt() RETURNS TRIGGER
AS
$check_ongoing_hunt$
BEGIN
    IF exists(
            SELECT *
            FROM ongoing_hunts oh
            WHERE (
                        oh.leading_player_id = NEW.leading_player_id
                    OR oh.leading_hunter_id = NEW.leading_hunter_id
                )
              AND oh.start_time <= NEW.end_time
        ) THEN
        RAISE EXCEPTION 'Hunt ends after the ongoing one';
    ELSE
        RETURN NEW;
    END IF;
END;
$check_ongoing_hunt$ LANGUAGE plpgsql;

CREATE TRIGGER check_ongoing_hunt
    BEFORE INSERT OR UPDATE
    ON sponsored_hunts
    FOR EACH ROW
EXECUTE PROCEDURE check_ongoing_hunt();

-- Hunt can't be started before the newest completed hunt
CREATE OR REPLACE FUNCTION check_finished_hunt() RETURNS TRIGGER
AS
$check_finished_hunt$
BEGIN
    IF exists(
            SELECT *
            FROM sponsored_hunts r
            WHERE (
                        r.regular_player_id = NEW.regular_player_id
                    OR r.leading_player_id = NEW.leading_player_id
                    OR r.leading_hunter_id = NEW.leading_hunter_id
                )
              AND r.end_time >= NEW.start_time
        ) THEN
        RAISE EXCEPTION 'Ongoing hunt starts after the completed one';
    ELSE
        RETURN NEW;
    END IF;
END;
$check_finished_hunt$ LANGUAGE plpgsql;

CREATE TRIGGER check_finished_hunt
    BEFORE INSERT OR UPDATE
    ON ongoing_hunts
    FOR EACH ROW
EXECUTE PROCEDURE check_finished_hunt();

-- INDEXES MonHunRise;
-- FK
CREATE INDEX ON owned_hunters USING hash (guild_id);
CREATE INDEX ON sponsored_hunts USING btree (src_id, dst_id);
CREATE INDEX ON sponsored_hunts USING btree (dst_id, src_id);
CREATE INDEX ON ongoing_hunts USING btree (src_id, dst_id);
CREATE INDEX ON ongoing_hunts USING btree (dst_id, src_id);

-- Query time
CREATE INDEX ON sponsored_hunts USING btree (start_time, end_time);

-- Constraints
CREATE INDEX ON sponsored_hunts USING btree (regular_player_id, end_time);

-- Level search
CREATE INDEX ON leading_hunters (level);



-- View for one of the selects

CREATE VIEW upgrades AS
(
SELECT sh.level AS requested_level,
       start_time AS time
FROM sponsored_hunts AS sh
         JOIN leading_hunters ON sh.leading_hunter_id = leading_hunters.id
WHERE sh.level < leading_hunters.level
    );
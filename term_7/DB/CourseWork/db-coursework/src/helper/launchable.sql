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

-- Can't end hunt before the start
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






-- FILL DATABASE MonHunRise;

INSERT INTO leading_hunters
(id, class, level, capacity, HR)
VALUES
    (1, 'SnS', 'high rank', 3, 'HR135'),
    (2, 'LS', 'god rank', 5, 'HR879'),
    (3, 'HH', 'low rank', 2, 'HR98'),
    (4, 'CB', 'god rank', 3, 'HR587'),
    (5, 'SA', 'god rank', 4, 'HR444'),
    (6, 'SnS', 'high rank', 2, 'HR123');

INSERT INTO hunters_guilds
(id, name, palico)
VALUES
    (1, 'Immortals', 'Chumuske'),
    (2, 'Farmers', 'Amogus'),
    (3, 'Followers of the Great Chokobo', 'Bob');

CREATE EXTENSION pgcrypto;

CREATE OR REPLACE FUNCTION add_regular_player(id_arg INT,
                                              name_arg VARCHAR(100),
                                              pass_arg TEXT)
    RETURNS BOOLEAN
AS
$$
DECLARE
    affected_rows INT;
BEGIN
    INSERT INTO regular_players (id, nickname, auth_token)
    VALUES (id_arg, name_arg, crypt(pass_arg, gen_salt('bf')))
    ON CONFLICT (id) DO NOTHING;
    GET DIAGNOSTICS affected_rows = ROW_COUNT;
    RETURN affected_rows > 0;
END;
$$ LANGUAGE plpgsql;

SELECT add_regular_player(1, 'Willy', 'Pass123');
SELECT add_regular_player(2, 'Billy', 'qwerty123');
SELECT add_regular_player(3, 'Kit', '907807098');
SELECT add_regular_player(4, 'Anonimus', 'sbao');
SELECT add_regular_player(5, 'Nameless', 'aboba');
SELECT add_regular_player(6, 'Mocus', 'passwerd');
SELECT add_regular_player(7, 'Mark', 'strng_pass');
SELECT add_regular_player(8, 'Palandai', 'unique_pass');
SELECT add_regular_player(9, 'Asasin', '123456987');
SELECT add_regular_player(10, 'Aron', '7919919abob');

INSERT INTO leading_players
(id, nickname, license_num)
VALUES
    (1, 'Dominator', 1337),
    (2, 'Daniel', 7071),
    (3, 'Tanaka', 123);

INSERT INTO locations
(id, country, field)
VALUES
    (1, 'Dundorma', 'Swamp'),
    (2, 'Dundorma', 'Volcano'),
    (3, 'Dundorma', 'Jungle'),
    (4, 'Jumbo Village', 'Snowy Mountains'),
    (5, 'Jumbo Village', 'Desert'),
    (6, 'Kokoto Village', 'Volcano'),
    (7, 'Kokoto Village', 'Snowy Mountains'),
    (8, 'Kokoto Village', 'Jungle'),
    (9, 'Kokoto Village', 'Great Forest');

INSERT INTO owned_hunters
(hunter_id, guild_id, rent_price)
VALUES
    (1, 1, 9000),
    (2, 2, 20000),
    (3, 2, 11000),
    (4, 2, 8000),
    (5, 1, 500000),
    (6, 3, 2500);

INSERT INTO sponsored_hunts
(id, src_id, dst_id, start_time, end_time, payment_type, price,
 level, party_cnt, leading_player_id, leading_hunter_id, regular_player_id, distance)
VALUES
    (1, 2, 6, now() - interval '5 hour', now() - interval '3 hour', 'zennies (gold)', 50000,
     'high rank', 2, 3, 4, 8, 9000),
    (2, 7, 9, now() - interval '2 hour', now() - interval '1 hour', 'tickets', 200,
     'god rank', 1, 1, 5, 3, 8000);

-- FUNCTIONS MonHunRise;

CREATE OR REPLACE FUNCTION finish_ride(leading_player_id_arg INT,
                                       price_arg INT,
                                       distance_arg INT,
                                       hunt_id_arg INT,
                                       end_time_arg TIMESTAMP,
                                       payment_type_arg payment_type_t)
    RETURNS BOOLEAN
AS
$$
DECLARE
    cur_hunt ongoing_hunts;
BEGIN
    DELETE
    FROM ongoing_hunts
    WHERE leading_player_id = leading_player_id_arg
    RETURNING *
        INTO cur_hunt;
    IF cur_hunt IS NULL THEN
        RETURN FALSE;
    ELSE
        INSERT INTO sponsored_hunts (id, src_id, dst_id, start_time,
                                     end_time, payment_type, price,
                                     level, party_cnt, leading_player_id,
                                     leading_hunter_id, regular_player_id, distance)
        VALUES (hunt_id_arg, cur_hunt.src_id, cur_hunt.dst_id, cur_hunt.start_time,
                end_time_arg, payment_type_arg, price_arg,
                cur_hunt.level, cur_hunt.party_cnt, leading_player_id_arg,
                cur_hunt.leading_hunter_id, cur_hunt.regular_player_id, distance_arg);
        RETURN TRUE;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- returns if change happened
-- delete if location is null
CREATE OR REPLACE FUNCTION modify_home_location(regular_hunter_id_arg INT,
                                                pass_arg TEXT,
                                                home_loc_arg INT)
    RETURNS BOOLEAN
AS
$$
DECLARE
    affected_rows INT;
BEGIN
    UPDATE regular_players
    SET home_location_id = home_loc_arg
    WHERE id = regular_hunter_id_arg
      AND auth_token = crypt(pass_arg, auth_token);
    GET DIAGNOSTICS affected_rows = ROW_COUNT;
    RETURN affected_rows > 0;
END;
$$ LANGUAGE plpgsql;

-- returns if change happened
-- delete if location is null
CREATE OR REPLACE FUNCTION modify_hunt_location(regular_hunter_id_arg INT,
                                                pass_arg TEXT,
                                                hunt_loc_arg INT)
    RETURNS BOOLEAN
AS
$$
DECLARE
    affected_rows INT;
BEGIN
    UPDATE regular_players
    SET hunt_location_id = hunt_loc_arg
    WHERE id = regular_hunter_id_arg
      AND auth_token = crypt(pass_arg, auth_token);
    GET DIAGNOSTICS affected_rows = ROW_COUNT;
    RETURN affected_rows > 0;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION hunts_hour_distribution(from_arg TIMESTAMP,
                                                   to_arg TIMESTAMP)
    RETURNS TABLE
            (
                hour INT,
                cnt  BIGINT
            )
    IMMUTABLE
AS
$$
BEGIN
    RETURN QUERY
        SELECT extract(HOUR FROM start_time)::INT as hour,
               count(1) as cnt
        FROM sponsored_hunts
        WHERE start_time BETWEEN from_arg AND to_arg
        GROUP BY extract(HOUR FROM start_time)
        ORDER BY extract(HOUR FROM start_time);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION upgrades_in_period(start_arg TIMESTAMP,
                                              end_arg TIMESTAMP)
    RETURNS TABLE
            (
                requested_level level_t,
                cnt             BIGINT
            )
    IMMUTABLE
AS
$$
BEGIN
    RETURN QUERY
        SELECT upgrades.requested_level,
               count(1) AS cnt
        FROM upgrades
        WHERE time BETWEEN start_arg AND end_arg
        GROUP BY upgrades.requested_level;
END;
$$ LANGUAGE plpgsql;

-- return total distance regular player achieved during sponsored hunts during an year
CREATE OR REPLACE FUNCTION regular_player_statistics_for_year(year_arg INT)
    RETURNS TABLE
            (
                id             INT,
                full_name      VARCHAR(100),
                rides_cnt      BIGINT,
                total_distance FLOAT,
                total_time     INTERVAL
            )
    IMMUTABLE
AS
$$
BEGIN
    RETURN QUERY
        WITH year_sponsored_hunts AS (
            SELECT *
            FROM sponsored_hunts
            WHERE extract(YEAR FROM start_time) = year_arg
              AND extract(YEAR FROM end_time) = year_arg
        )
        SELECT regular_players.id AS id,
               regular_players.nickname AS nickname,
               count(year_sponsored_hunts.id) AS hunts_cnt,
               coalesce(sum(distance) / 1000.0, 0)::FLOAT AS total_distance,
               coalesce(sum(end_time - start_time), '0 minutes'::INTERVAL) AS total_time
        FROM regular_players
                 LEFT OUTER JOIN year_sponsored_hunts ON regular_players.id = year_sponsored_hunts.regular_player_id
        GROUP BY regular_players.id, regular_players.nickname;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION validate_regular_player(id_arg INT,
                                                   pass_arg TEXT)
    RETURNS BOOLEAN
    IMMUTABLE
AS
$$
BEGIN
    RETURN exists(
            SELECT *
            FROM regular_players
            WHERE id = id_arg
              AND auth_token = crypt(pass_arg, auth_token)
        );
END;
$$ LANGUAGE plpgsql;

-- returns distribution by payment type
WITH payment_cnt AS (
    SELECT payment_type,
           count(1) as cnt
    FROM sponsored_hunts
    GROUP BY payment_type
)
SELECT payment_type,
       cnt * 100.0 / (SELECT sum(cnt) FROM payment_cnt) AS percent
FROM payment_cnt;

CREATE VIEW upgrades AS
(
SELECT sh.level AS requested_level,
       start_time AS time
FROM sponsored_hunts AS sh
         JOIN leading_hunters ON sh.leading_hunter_id = leading_hunters.id
WHERE sh.level < leading_hunters.level
    );
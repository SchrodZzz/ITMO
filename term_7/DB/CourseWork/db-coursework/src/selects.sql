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
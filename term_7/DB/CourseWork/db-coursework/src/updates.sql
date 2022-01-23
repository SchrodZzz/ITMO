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
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
DO
$do$
    BEGIN
        IF NOT EXISTS(SELECT
                      FROM pg_roles
                      WHERE rolname = 'deberts') THEN
            CREATE USER deberts WITH ENCRYPTED PASSWORD 'password123';
            GRANT ALL PRIVILEGES ON DATABASE postgres TO deberts;
            GRANT SELECT ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT UPDATE ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to deberts;
        END IF;
    END
$do$;

CREATE TABLE IF NOT EXISTS players
(
    plr_id                SERIAL PRIMARY KEY,
    plr_name              VARCHAR(50) UNIQUE NOT NULL,
    plr_last_game_result  VARCHAR(4)         NOT NULL,
    plr_total_wins        INT                NOT NULL,
    plr_total_loses       INT                NOT NULL,
    plr_win_rate          NUMERIC(5, 2),
    plr_actual_win_streak INT                NOT NULL,
    plr_best_win_streak   INT                NOT NULL
);

CREATE TABLE IF NOT EXISTS parties
(
    prt_id           SERIAL PRIMARY KEY,
    prt_plr_id       SERIAL,
    prt_player_score INT,
    prt_bot_score    INT,
    prt_status       VARCHAR(8) NOT NULL,
    CONSTRAINT fk_player
        FOREIGN KEY (prt_plr_id)
            REFERENCES players (plr_id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS rounds
(
    rnd_id            SERIAL,
    rnd_number        INT,
    rnd_plr_id        SERIAL,
    rnd_prt_id        SERIAL,
    rnd_round_deck    jsonb,
    rnd_trump_deck    jsonb,
    rnd_player_deck   jsonb,
    rnd_bot_deck      jsonb,
    rnd_player_points INT,
    rnd_bot_points    INT,
    rnd_turn          VARCHAR(5) NOT NULL,
    rnd_trump_picker  VARCHAR(5),
    rnd_status        VARCHAR(8) NOT NULL,

    PRIMARY KEY (rnd_id, rnd_number),

    CONSTRAINT fk_player
        FOREIGN KEY (rnd_plr_id)
            REFERENCES players (plr_id)
            ON DELETE SET NULL,

    CONSTRAINT fk_parties
        FOREIGN KEY (rnd_prt_id)
            REFERENCES parties (prt_id)
            ON DELETE SET NULL
);

DO
$do$
    BEGIN
        IF NOT EXISTS(SELECT
                      FROM players
                      WHERE plr_name = 'player') THEN

            INSERT INTO players( plr_name
                               , plr_last_game_result
                               , plr_total_wins
                               , plr_total_loses
                               , plr_win_rate
                               , plr_actual_win_streak
                               , plr_best_win_streak)
            VALUES ('player', 0, 0, 0, 0, 0, 0);
        END IF;
    END
$do$;

CREATE TABLE dungeon_solutions (
    id BIGSERIAL PRIMARY KEY,
    player_id VARCHAR(255) NOT NULL,
    min_health_required INT NOT NULL,
    variant VARCHAR(10) NOT NULL
);

CREATE TABLE experiment_exposures (
    id BIGSERIAL PRIMARY KEY,
    player_id VARCHAR(255) NOT NULL,
    experiment_key VARCHAR(255) NOT NULL,
    variant VARCHAR(10) NOT NULL,
    execution_time_ms DOUBLE PRECISION
);

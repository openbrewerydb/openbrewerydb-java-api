CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE SCHEMA openbrewerydb

CREATE TABLE IF NOT EXISTS openbrewerydb.breweries (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    brewery_type VARCHAR,
    city VARCHAR NOT NULL,
    state VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    location geography(POINT,4326) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS trgm_idx ON openbrewerydb.breweries USING GIN (name gin_trgm_ops);

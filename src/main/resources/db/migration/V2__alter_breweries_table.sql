ALTER TABLE openbrewerydb.breweries
ADD COLUMN obdb_id VARCHAR NOT NULL DEFAULT 'OBDBID',
ADD COLUMN street VARCHAR,
ADD COLUMN address2 VARCHAR,
ADD COLUMN address3 VARCHAR,
ADD COLUMN county_province VARCHAR,
ADD COLUMN postal_code VARCHAR,
ADD COLUMN phone VARCHAR,
ADD COLUMN website_url VARCHAR,
ADD COLUMN tags VARCHAR;

CREATE FUNCTION format_obdb_id(name VARCHAR, city VARCHAR)
RETURNS VARCHAR AS $$
DECLARE
    stripped_name VARCHAR;
    stripped_city VARCHAR;

BEGIN
    stripped_name = regexp_replace(name, '[^A-Za-z0-9 ]', '', 'g');
    stripped_city = regexp_replace(city, '[^A-Za-z0-9 ]', '', 'g');

    RETURN regexp_replace(CONCAT(LOWER(stripped_name), ' ', LOWER(stripped_city)), ' ', '-', 'g');
END;
$$ LANGUAGE plpgsql;

UPDATE openbrewerydb.breweries dest
set obdb_id = format_obdb_id(src.name, src.city)
from openbrewerydb.breweries src
where dest.obdb_id = src.obdb_id
and dest.obdb_id = 'OBDBID';

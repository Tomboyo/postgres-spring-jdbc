-- Arrays.
--
-- Arrays can be used to denormalize a one-to-many relationship, such as to
-- avoid joins.
--
-- See:
-- https://www.postgresql.org/docs/14/arrays.html
-- https://www.postgresql.org/docs/14/indexes-types.html#INDEXES-TYPES-GIN
create table games (
    id     serial primary key,
    teams  text[2],
    points integer[2],
    winner text generated always as (
      case
        when points[1] > points[2] then teams[1]
        when points[2] > points[1] then teams[2]
        else null
      end
    ) stored
);

create index games_teams_idx on games using GIN (teams);
create index games_winner_idx on games using btree (winner);

-- Jsonb.
--
-- Jsonb may be used to save json documents. Similar to arrays, this is good for
-- denormalized data.
--
-- See:
-- https://www.postgresql.org/docs/14/datatype-json.html
create table events (
  id  serial primary key,
  doc jsonb
);

create index events_jsonb_idx on events using GIN (doc);
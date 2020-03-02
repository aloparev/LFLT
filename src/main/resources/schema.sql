feedback auto fenerated (
    id        | bigint                   |           | not null |
     os        | character varying(255)   |           | not null |
     device    | character varying(255)   |           | not null |
     email     | character varying(255)   |           | not null |
     msg       | text                     |           | not null |
     name      | character varying(255)   |           | not null |
     type      | character varying(255)   |           | not null |
     timestamp | timestamp with time zone |           |          | now()
)

CREATE TABLE stocks (
    symbol varchar(9) PRIMARY KEY,
    name varchar(99),
    market varchar(9),
    country varchar(9),
    sector varchar(99),
    industry varchar(99),
    tstamp timestamptz default now()
);

CREATE TABLE lots (
    symbol varchar(9) PRIMARY KEY REFERENCES stocks(symbol),
    units INTEGER,
    ip REAL,
    ipt REAL,
    tstamp timestamptz
);
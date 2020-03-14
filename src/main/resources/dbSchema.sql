DROP TABLE feedback;
CREATE TABLE feedback (
    id SERIAL PRIMARY KEY,

)
feedback auto fenerated (
    id        | bigint                   |           | not null |
     os        | character varying(255)   |           | not null |
     device    | character varying(255)   |           | not null |
     email     | character varying(255)   |           | not null |
     msg       | text                     |           | not null |
     name      | character varying(255)   |           | not null |
     type      | character varying(255)   |           | not null |
     timestamp | timestamp with time zone |           |          | now()
);

DROP TABLE stocks;
CREATE TABLE stocks (
    symbol varchar(9) PRIMARY KEY,
    name varchar(99),
    market varchar(9),
    country varchar(9),
    sector varchar(99),
    industry varchar(99),
    tstamp TIMESTAMPTZ default now()
);

DROP TABLE lots;
CREATE TABLE lots (
    id INTEGER PRIMARY KEY,
    symbol varchar(9) REFERENCES stocks(symbol),
    portfolio_id integer references portfolios(id),
    units INTEGER,
    ip REAL,
    ipt REAL,
    tstamp TIMESTAMPTZ
);

DROP TABLE portfolioss;
CREATE TABLE portfolios (
    id INTEGER PRIMARY KEY,
    name VARCHAR(99),
    info VARCHAR(999),
    type VARCHAR(99),
    funds INTEGER,
    epoch INTEGER,
    tstamp TIMESTAMPTZ default now(),
    ustamp TIMESTAMPTZ default now()
);
insert into portfolios (id, name, info, type, funds, epoch, ustamp)
values (0, 'Random Portfolio', 'random stock buy', 'RANDOM', 500, 3, '2020-03-10 04:31:57.537833+00');

CREATE TABLE quotes (
    id INTEGER PRIMARY KEY,
    symbol varchar(9),
    price REAL,
    changesPercentage REAL,
    change REAL,
    dayLow REAL,
    dayHigh REAL,
    yearLow REAL,
    yearHigh REAL,
    marketCap BIGINT,
    priceAvg50 REAL,
    priceAvg200 REAL,
    volume BIGINT,
    avgVolume BIGINT,
    exhange VARCHAR(9),
    open REAL,
    previousClose REAL,
    eps REAL,
    pe REAL,
    earningsAnnouncement TIMESTAMPTZ,
    sharesOutstanding BIGINT,
    tstamp TIMESTAMPTZ
);
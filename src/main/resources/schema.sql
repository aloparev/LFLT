DROP TABLE IF EXISTS stocks CASCADE;
DROP TABLE IF EXISTS portfolios CASCADE ;
DROP TABLE IF EXISTS lots cascade ;
drop table IF EXISTS portfolios_lots;
DROP TABLE IF EXISTS stocks;
DROP TABLE IF EXISTS feedbacks;
DROP TABLE IF EXISTS quotes;
DROP TABLE IF EXISTS mquotes;
drop sequence if exists hibernate_sequence;

CREATE SEQUENCE hibernate_sequence START 2000;

CREATE TABLE stocks (
    index integer,
    symbol varchar(9) PRIMARY KEY,
    name varchar(49),
    market varchar(9),
    land varchar(9),
    sector varchar(99),
    industry varchar(99),
    tstamp TIMESTAMPTZ default now()
);

CREATE TABLE portfolios (
    id INTEGER PRIMARY KEY,
    name VARCHAR(99),
    info VARCHAR(999),
    type VARCHAR(99),
    balance REAL,
    funds REAL,
    epochs INTEGER,
    ustamp TIMESTAMPTZ,
    tstamp TIMESTAMPTZ
);

CREATE TABLE lots (
    id INTEGER PRIMARY KEY,
    symbol varchar(9) REFERENCES stocks(symbol),
    name varchar(49),
    portfolio_id integer references portfolios(id),
    units INTEGER,
    ip REAL,
    ipt REAL,
    tstamp TIMESTAMPTZ
);

-- create table portfolios_lots (
--     portfolio_id integer references portfolios(id),
--     lots_id integer references lots(id)
-- );

CREATE TABLE feedbacks (
    id integer PRIMARY KEY,
    type varchar(99),
    device varchar(99),
    browser varchar(99),
    msg varchar(999),
    name varchar(99),
    email varchar(99),
    tstamp TIMESTAMPTZ
);

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

CREATE TABLE mquotes (
    id INTEGER PRIMARY KEY,
    symbol varchar(9),
    price REAL,
    changePct REAL,
    changeAbs REAL,
);
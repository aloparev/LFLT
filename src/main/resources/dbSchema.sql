DROP TABLE feedback;
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

DROP TABLE portfolios;
CREATE TABLE portfolios (
    id INTEGER PRIMARY KEY,
    name VARCHAR(99),
    info VARCHAR(999),
    type VARCHAR(99),
    funds INTEGER,
    epochs INTEGER,
    ustamp TIMESTAMPTZ default now(),
    tstamp TIMESTAMPTZ
);
insert into portfolios (id, name, info, type, funds, epochs, ustamp, tstamp)
values (0, 'Random Portfolio', 'New purchase for 1k$ every day', 'RANDOM', 1000, 1, '2020-03-15 10:38:57.537833+00', '2020-03-15 19:55:00+00');

drop table portfolios_lots;
create table portfolios_lots (
    portfolio_id integer references portfolios(id),
    lots_id integer references lots(id)
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
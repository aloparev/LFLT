insert into portfolios (id, name, info, type, funds, epochs, ustamp, tstamp)
values (0, 'Random Portfolio', 'New purchase for 1k$ every day', 'RANDOM', 1000, 1, '2020-03-15 10:38:57.537833+00', '2020-03-15 19:55:00+00');

insert into stocks(index, symbol, name, market, land, sector, industry, tstamp)
select index, symbol, name, market, land, sector, industry, tstamp from stocks500;
-- pg_dump --table=export_table --data-only --column-inserts my_database > data.sql


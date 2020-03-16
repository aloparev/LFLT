insert into portfolios (id, name, info, type, funds, epochs, ustamp, tstamp)
values (0, 'Random Portfolio', 'New purchase for 1k$ every day', 'RANDOM', 1000, 1, '2020-03-15 10:38:57.537833+00', '2020-03-15 19:55:00+00');

insert into stocks(symbol, name, market, land, sector, industry, tstamp)
values ('MMM', 0, 'Industrial Conglomerates', 'US', 'S&P 500', '3M Company', 'Industrials', '2020-03-16 17:38:52.560484')
-- pg_dump --table=export_table --data-only --column-inserts my_database > data.sql
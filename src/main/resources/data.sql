
insert into stocks(index, symbol, name, market, land, sector, industry, tstamp)
select index, symbol, name, market, land, sector, industry, tstamp from stocks500;
-- pg_dump --table=export_table --data-only --column-inserts my_database > data.sql

insert into portfolios (id, name, info, type, funds, epochs, ustamp, tstamp)
values (0, 'Random Portfolio', 'New purchase for 1k$ every day', 'RANDOM', 1000, 1, '2020-03-15 10:38:57.537833+00', '2020-03-15 19:55:00+00');

insert into lots(id, symbol, name, units, ip, ipt, tstamp)
values (1628, 'ARE', 'Alexandria Real Estate Equities', 7, 130.91, 916.37, '2020-03-16 18:58:02.01+00');

insert into lots(id, symbol, name, units, ip, ipt, tstamp)
values (1629, 'MCD', 'McDonald''s Corp.', 6, 153.464, 920.786, '2020-03-16 18:59:01.458+00');

--  1630 | MLM    |              |     5 |  176.13 |  880.65 | 2020-03-16 19:00:01.742+00
--  1631 | APA    |              |   166 |   6.185 | 1026.71 | 2020-03-16 19:01:01.571+00
--  1632 | WFC    |              |    35 |  28.745 | 1006.08 | 2020-03-16 19:02:01.628+00

insert into portfolios_lots(portfolio_id, lots_id)
values(0, 1628);

insert into portfolios_lots(portfolio_id, lots_id)
values(0, 1629);


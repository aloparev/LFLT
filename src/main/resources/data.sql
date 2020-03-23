
insert into stocks(index, symbol, name, market, land, sector, industry, tstamp)
select index, symbol, name, market, land, sector, industry, tstamp from stocks500;
-- pg_dump --table=export_table --data-only --column-inserts my_database > data.sql

insert into portfolios (id, name, info, type, funds, epochs, ustamp, tstamp)
values (0, 'Random Portfolio', 'New purchase for 1k$ every day', 'RANDOM', 1000, 1, '2020-03-15 10:38:57.537833+00', '2020-03-15 19:55:00+00');

-- insert into portfolios (id, name, info, type, funds, epochs, tstamp)
-- values (1, 'Random Portfolio', 'test updater', 'RANDOM', 1000, 1, '2020-03-15 19:55:00+00');
--
-- insert into portfolios (id, name, info, type, funds, epochs, ustamp, tstamp)
-- values (2, 'Random Portfolio', 'test updater', 'RANDOM', 1000, 1, '2020-04-01', '2020-03-15 19:55:00+00');

insert into lots(id, portfolio_id, symbol, name, units, ip, ipt, tstamp)
values (1628, 0, 'ARE', 'Alexandria Real Estate Equities', 7, 130.91, 916.37, '2020-03-16 18:58:02.01+00');

-- insert into portfolios_lots(portfolio_id, lots_id)
-- values(0, 1628);

insert into lots(id, portfolio_id, symbol, name, units, ip, ipt, tstamp)
values (1629, 0, 'MCD', 'McDonald''s Corp.', 6, 153.464, 920.786, '2020-03-16 18:59:01.458+00');

-- insert into portfolios_lots(portfolio_id, lots_id)
-- values(0, 1629);

insert into lots(id, portfolio_id, symbol, name, units, ip, ipt, tstamp)
values (1633, 0, 'FB', 'Facebook, Inc.', 10, 100, 1000, '2020-03-17 21:35');

-- insert into portfolios_lots(portfolio_id, lots_id)
-- values(0, 1633);


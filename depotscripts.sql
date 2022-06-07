



-- drop database `depot` ;
USE `depot` ;
select * from drivers;
select * from licences;
select * from categories;




ALTER TABLE drivers ADD CHECK(age>=18)
ALTER TABLE categories ADD UNIQUE (name);
ALTER TABLE models ADD UNIQUE (name);
ALTER TABLE stations ADD UNIQUE (name);
ALTER TABLE routes_has_stations MODIFY id INTEGER NOT NULL AUTO_INCREMENT;
ALTER TABLE buses ADD UNIQUE (drivers_id)
ALTER TABLE buses ADD UNIQUE (name)

select * from drivers;
select * from buses;
select * from licences;
select * from categories_has_licences;
select * from categories ORDER BY id asc;
select * from models
select * from managers
select * from stations
select * from routes
select * from routes_has_stations
select * from types
select * from depots;
select * from maintenance
select * from services
select * from mechanics
SET FOREIGN_KEY_CHECKS=0;
SET FOREIGN_KEY_CHECKS=1;





insert into routes_has_stations(routes_id, stations_id, routes_has_stations.order) values 
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(1, 5, 5),
(1, 6, 6),
(1, 7, 7),
(1, 8, 8),
(1, 9, 9),
(1, 10, 10),
(1, 4, 11),
(1, 1, 12)
insert into licences(expiration) values("2022-12-12");
insert into licences(expiration) values("2025-11-10");
insert into licences(expiration) values("2026-09-11");
insert into categories_has_licences(categories_id, licences_id) values (1, 1), (2, 1), (3, 1)
insert into categories_has_licences(categories_id, licences_id) values (1, 2), (2, 2)
insert into drivers(name, age, licences_id) values ('Oleg', 18, 1)
insert into drivers(name, age, licences_id) values ('Max', 22, 2)
insert into categories(name) values("AM"), ("A"),("B"), ("C"), ("D"), ("F"), ("I"), ("BE"), ("CE"),  ("DE");
SELECT * FROM categories
UPDATE categories SET name = ("DE") WHERE id = 10
insert into models(name) values ('MAZ-103'), ('MAZ-104'), ('MAZ-105'), ('MAZ-107'), ('MAZ-256')
insert into types(name) values ('in-city'), ('inter-city')

insert into stations(name) values
 ('DS "Zachad-3"'),
 ('Zachad-3'),
 ('Kamiennaja Horka st.m.'),
 ('Niomanskaja'), 
 ('Brykieta'), 
 ('Kamiennahorskaja, 14'), 
 ('Kamiennahorskaja'),
 ('Kamiennaja Horka-5'),
 ('Kazimirauskaja'),
 ('Mikrarajon Kamiennaja Horka')
 insert into stations(name) values
 ('Lidskaja'), 
 ('Nalibockaja'), 
 ('Halubka vul.'),
 ('Pereulok Kolesnikova'),
 ('HC "Zdanovicy"')
 insert into routes_has_stations(routes_id, stations_id, routes_has_stations.order) values
 (2, 1, 1),
 (2, 2, 2),
 (2, 3, 3),
 (2, 4, 4),
 (2, 10, 5),
 (2, 9, 6),
 (2, 11, 7),
 (2, 12, 8),
 (2, 13, 9),
 (2, 14, 10),
 (2, 15, 11)
 select * from stations
 select * from models
 
 insert into routes(name) values ('DS "Zachad-3 - DS "Zachad-3"')
 insert into routes(name) values ('DS "Zachad-3 - HC "Zdanovicy"')
 insert into buses(name, types_id, drivers_id, models_id, routes_id, depots_id) 
 values ('159', 1, 1, 1, 1, 1)
 insert into buses(name, types_id, drivers_id, models_id, routes_id, depots_id) 
 values ('60', 1, 2, 2, 2, 1)
 delete
 select * from buses
 UPDATE buses SET routes_id = 2 WHERE name = '60';
 insert into depots(name, managers_id) values ('DS "Zachad-3', 1)
 insert into managers(name) values ('Michael'), ('Nikita')
 insert into managers(name) values ('Sergey'), ('Bogdan')
 select * from managers
 select * from depots
 insert into mechanics(name) values ('Artiyom'), ('Alexander')
 insert into services(type) values ('Engine overhaul'), ('Steering repair'), ('Repair of brake system'), ('Repair of fuel system')
 insert into maintenance(buses_id, mechanics_id, services_id, date) values (1, 1, 1, '2022-10-10')
 
 
 
 select buses.name, drivers.name, routes.name from buses
 inner join routes
 on routes.id = buses.routes_id
 inner join drivers
 on drivers.id = buses.drivers_id


SELECT name, age, expiration FROM drivers INNER JOIN licences ON drivers.licences_id = licences.id;

SELECT expiration, licences_id, name FROM licences INNER JOIN categories_has_licences ON licences.id = categories_has_licences.licences_id INNER JOIN categories ON categories_has_licences.id = categories.id;


SELECT GROUP_CONCAT(name) as name FROM categories

SELECT GROUP_CONCAT(name), AVG(age) from drivers

SELECT * from drivers
inner join licences
on drivers.licences_id = licences.id
inner join categories_has_licences 
on licences.id = categories_has_licences.licences_id
inner join categories
on categories.id = categories_has_licences.categories_id



SELECT drivers.name, GROUP_CONCAT(categories.name) from drivers
inner join licences
on drivers.licences_id = licences.id
inner join categories_has_licences 
on licences.id = categories_has_licences.licences_id
inner join categories
on categories.id = categories_has_licences.categories_id
GROUP BY(drivers.name)

SELECT drivers.name, AVG(age) from drivers
GROUP BY(drivers.name)

SELECT routes.name, routes_has_stations.order, stations.name FROM routes
INNER JOIN routes_has_stations
ON routes.id = routes_has_stations.routes_id
INNER JOIN stations
ON stations.id = routes_has_stations.stations_id
GROUP BY (routes_has_stations.order)

SELECT * FROM buses 
RIGHT JOIN models
ON buses.models_id = models.id
LEFT JOIN types
ON buses.types_id = types.id
RIGHT JOIN drivers
ON drivers.id = buses.drivers_id
LEFT JOIN licences 
ON drivers.licences_id = licences.id
LEFT JOIN categories_has_licences
ON categories_has_licences.licences_id = licences.id
LEFT JOIN categories
ON categories.id = categories_has_licences.categories_id

SELECT depots.name, GROUP_CONCAT(buses.name) FROM depots 
INNER JOIN buses 
ON depots.id = buses.depots_id
GROUP BY depots.name
WHERE depots_id = 1

SELECT * from buses
SELECT * FROM maintenance

SELECT licences.id, licences.expiration, categories.name FROM licences 
INNER JOIN categories_has_licences
ON licences.id = categories_has_licences.licences_id
INNER JOIN categories
ON categories.id = categories_has_licences.categories_id
WHERE categories.id IN (1)

select * from licences

select * from categories_has_licences

select * from categories

SELECT * FROM categories 
INNER JOIN categories_has_licences
ON categories_has_licences.categories_id = categories.id
INNER JOIN licences 
ON categories_has_licences.licences_id = licences.id
WHERE licences.id IN (?)

SELECT licences.id, licences.expiration FROM licences INNER JOIN drivers ON drivers.licences_id = licences.id WHERE drivers.id IN(1)


SELECT drivers.id, drivers.name from drivers
INNER JOIN buses
ON buses.drivers_id = drivers.id
WHERE buses.id IN (1)

select * from licences
select * from drivers where id in (1)
insert into drivers(name, age, licences_id) values (?, ?, ?)
UPDATE drivers SET name = ? , age = ? , licences_id = ? WHERE id in (?)
insert into
select * from buses
UPDATE buses 
SET name = 'test', 
types_id = 2, 
drivers_id = 3, 
models_id = 4, 
routes_id = 2, 
depots_id = 1
WHERE id IN (4)

insert into buses(name, types_id, drivers_id, models_id, routes_id, depots_id) 
 values ('kek', 1, 3, 1, 1, 1)
 
 getAllStationsByRouteId
 SELECT routes_has_stations.routes_id, routes_has_stations.stations_id, stations.name, routes_has_stations.order FROM routes_has_stations
INNER JOIN routes
 ON routes.id = routes_has_stations.routes_id
 INNER JOIN stations
 ON stations.id = routes_has_stations.stations_id
 WHERE routes.id = 1
 ORDER BY routes_has_stations.order ASC
 
 getStationNameByOrder
 SELECT stations.name, routes_has_stations.order FROM routes_has_stations
 INNER JOIN routes
 ON routes.id = routes_has_stations.routes_id
 INNER JOIN stations
 ON stations.id = routes_has_stations.stations_id
 WHERE (routes.id = 1) AND (routes_has_stations.order = 10)
 
 select * from routes_has_stations
 
 Route{name ='test', stations=[Station{name="Station name", }, Sta
 
 SELECT types.name FROM types INNER JOIN buses ON types.id = buses.types_id WHERE buses.id IN (?)
 SELECT buses.id, models.name FROM models INNER JOIN buses ON models.id = buses.models_id WHERE buses.id IN(2)
 SELECT buses.id, routes.name FROM routes INNER JOIN buses ON routes.id = buses.routes_id WHERE buses.id IN(?)
 SELECT buses.id, depots.name FROM depots INNER JOIN buses ON depots.id = buses.routes_id WHERE buses.id IN(?)
 select * from maintenance
 select * from drivers
 
 select * from buses
 select * from models
 select * from routes
 delete from buses WHERE id = 4
 join models
 INSERT INTO buses(name, types_id, drivers_id, models_id, routes_id, depots_id) VALUES (?, ?, ?, ?, ?, ?)
 UPDATE buses SET name = (?), types_id = (?), drivers_id = (?), models_id = (?), routes_id = (?), depots_id = (?) WHERE id = ?
 select * from maintenance
INSERT INTO maintenance(date, services_id, mechanics_id, buses_id) VALUES (?, ?, ?, ?)
UPDATE maintenance SET date = ?, services_id = ?, mechanics_id = ?, buses_id = ? WHERE id IN(?)
SELECT buses.id, buses.name, types.name FROM buses INNER JOIN types ON types.id = buses.types_id WHERE types.id = 1

 

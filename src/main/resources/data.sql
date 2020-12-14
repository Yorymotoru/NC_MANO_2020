INSERT INTO building(id, address, number_of_floors, residential)
VALUES (1, '1', 9, true),
       (2, '2', 3, false),
       (3, '3', 16, true);

INSERT INTO tenant(id, building_id, apartment_no, first_name, second_name)
VALUES (1, 1, 1, 'Иван', 'Иванов'),
       (2, 1, 2, 'Пётр', 'Петров'),
       (3, 2, 1, 'Фёдор', 'Фёдоров')
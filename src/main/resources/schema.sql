DROP TABLE IF EXISTS building;
DROP TABLE IF EXISTS tenant;

CREATE TABLE building
(
    id               INT(4) IDENTITY NOT NULL,
    address          VARCHAR(30),
    number_of_floors INT(4),
    residential      BOOL
);

CREATE TABLE tenant
(
    id           INT(4) IDENTITY NOT NULL,
    building_id  VARCHAR(30),
    apartment_no INT(4),
    first_name   VARCHAR(20),
    second_name  VARCHAR(20)
)
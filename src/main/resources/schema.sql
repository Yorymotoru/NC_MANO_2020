DROP TABLE IF EXISTS building;

CREATE TABLE building (
    address VARCHAR(30) IDENTITY NOT NULL,
    number_of_floors INT(4),
    residential BOOL
    )
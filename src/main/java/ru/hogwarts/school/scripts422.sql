CREATE TABLE cars
id SERIAL PRIMARY KEY,
brand VARCHAR(255) NOT NULL,
model VARCHAR(255) NOT NULL,
cost NUMERIC NOT NULL;

CREATE TABLE mans
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
age INTEGER,
license BOOLEAN,
car_id REFERENCES cars (id);

Описание структуры: у каждого человека есть машина. Причем несколько человек могут пользоваться одной машиной.
У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
У каждой машины есть марка, модель и стоимость. Также не забудьте добавить таблицам первичные ключи и связать их.
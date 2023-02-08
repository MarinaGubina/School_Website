CREATE TABLE people(
    id SERIAL PRIMARY KEY ,
    name TEXT NOT NULL,
    age INTEGER NOT NULL,
    license BOOLEAN NOT NULL,
    cars_id SERIAL REFERENCES cars (id)
);

CREATE TABLE cars(
    id SERIAL PRIMARY KEY ,
    brand TEXT NOT NULL ,
    model TEXT NOT NULL ,
    price NUMERIC(9,2)
);
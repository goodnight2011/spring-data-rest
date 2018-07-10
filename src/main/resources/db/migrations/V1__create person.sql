create table person(
  id INT PRIMARY KEY NOT NULL ,
  first_name VARCHAR(20),
  last_name VARCHAR(20)
);

insert into person(id, first_name, last_name) VALUES (1, 'Alexandr', 'Busovikov'),(2, 'Kuzma', 'Petrov'), (3, 'John', 'Smith');

create SEQUENCE person_pk_seq start with 1 INCREMENT BY 1;

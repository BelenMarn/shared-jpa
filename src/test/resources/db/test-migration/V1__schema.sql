CREATE TABLE friend (
    id_friend BIGINT PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE expense (
     id_expense BIGINT PRIMARY KEY,
     id_friend BIGINT,
     amount DECIMAL,
     description VARCHAR(50),
     expense_date DATETIME,
     FOREIGN KEY (id_friend) REFERENCES friend(id_friend) ON DELETE CASCADE
);

INSERT INTO friend (id_friend, name) VALUES (1, 'Juan');
INSERT INTO friend (id_friend, name) VALUES (2, 'María');
INSERT INTO friend (id_friend, name) VALUES (3, 'Pedro');
INSERT INTO friend (id_friend, name) VALUES (4, 'Ismael');

INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (1, 1, 50.00, 'Trenes', '2024-04-10 12:30:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (2, 1, 50.00, 'Comida en restaurante', '2024-04-11 12:30:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (3, 2, 20.00, 'Cine', '2024-04-10 20:00:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (4, 2, 30.00, 'Comida', '2024-04-10 21:00:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (5, 3, 40.00, 'Excursión', '2024-04-15 12:30:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (6, 3, 50.00, 'Materiales y subida.', '2024-04-15 10:30:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (7, 4, 50.00, 'Materiales.', '2024-04-10 10:30:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (8, 4, 10.00, 'Cafés', '2024-04-11 12:30:00');
INSERT INTO expense (id_expense, id_friend, amount, description, expense_date) VALUES (9, 2, 20.00, 'Comida en restaurante', '2024-04-09 16:00:00');

INSERT INTO users (username, firstname, lastname, created_on, updated_on) VALUES ('admin', 'admin', 'admin', current_date, current_date);
INSERT INTO users (username, firstname, lastname, created_on, updated_on) VALUES ('public', 'public', 'user', current_date, current_date);
INSERT INTO users (username, firstname, lastname, created_on, updated_on) VALUES ('analyst', 'analyst', 'example', current_date, current_date);
INSERT INTO users (username, firstname, lastname, created_on, updated_on) VALUES ('grebennikovas', 'Andrey', 'Gre', current_date, current_date);

INSERT INTO sources (name, type, netloc, port, dbname, params, username, password) VALUES ('default', 'POSTGRESQL', 'localhost', '5433', 'viewpoint', '', 'postgres', 'postgres');
INSERT INTO sources (name, type, netloc, port, dbname, params, username, password) VALUES ('external', 'MYSQL', '1.1.1.1', '5432', 'public', '', 'admin', '12345');

INSERT INTO datasets (sql_query, user_id, created_on, updated_on, source_id) VALUES ('SELECT * FROM users;', 4, current_date, current_date, 1);
INSERT INTO datasets (sql_query, user_id, created_on, updated_on, source_id) VALUES ('SELECT * FROM users WHERE firstname = {:p_name};', 4, current_date, current_date, 1);
INSERT INTO datasets (sql_query, user_id, created_on, updated_on, source_id) VALUES ('SELECT * FROM customers;', 4, current_date, current_date, 1);

INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (1, 'id','int', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (1, 'created_on','timestamp', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (1, 'updated_on','timestamp', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (1, 'firstname','varchar', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (1, 'lastname','varchar', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (1, 'username','varchar', current_date, current_date);

INSERT INTO parameters (dataset_id, name, type) VALUES (2,'p_name','String');

INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (2, 'id','int', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (2, 'created_on','timestamp', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (2, 'updated_on','timestamp', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (2, 'firstname','varchar', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (2, 'lastname','varchar', current_date, current_date);
INSERT INTO columns (dataset_id, name, type, created_on, updated_on) VALUES (2, 'username','varchar', current_date, current_date);
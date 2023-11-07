-- password is the same as username
INSERT INTO users (username, firstname, lastname, email, role, password, is_active) VALUES ('jdog', 'Jorel', 'Decker', 'jdog@example.com', 'ADMIN', '$2y$10$ECiw.NXvbbabZmzfx9nBe.9SfFJy9mxOAtpUPMKTe0I6.F70CeBZy', true);
INSERT INTO users (username, firstname, lastname, email, role, password, is_active) VALUES ('funnyman', 'Dylan', 'Alvarez', 'funnyman@example.com', 'ANALYST', '$2y$10$YRIsc64l3WRCLO54SZw0yODyh1ZAiZ70lqmcGlaIUq5ggH6FQqXE.', true);
INSERT INTO users (username, firstname, lastname, email, role, password, is_active) VALUES ('charliescene', 'Jordon', 'Terrell', 'charliescene@example.com', 'PUBLIC', '$2y$10$rTVKGWe9vhxm07K7.9wCsOKp8dkGSzmdhOQ2k/56yBRKQMGxtDQW2', false);
INSERT INTO users (username, firstname, lastname, email, role, password, is_active) VALUES ('danny', 'Daniel', 'Murillo', 'danny@example.com', 'PUBLIC', '$2y$10$hLOSMYt.eZbKcahKGDJn9u0bpaqElt6NXKVqTaS8LW2H1lBZWWx1u', true);
INSERT INTO users (username, firstname, lastname, email, role, password, is_active) VALUES ('johnny3tears', 'George', 'Ragan', 'johnny3tears@example.com', 'ANALYST', '$2y$10$mMA2/vpavfUlg8YD6PSkwuyORLWhbim4kzZiNBCO0VYEf5o1v0bvu', true);

INSERT INTO sources (name, type, netloc, port, dbname, params, username, password) VALUES ('default', 'POSTGRESQL', 'localhost', '5433', 'viewpoint', '', 'postgres', 'postgres');
INSERT INTO sources (name, type, netloc, port, dbname, params, username, password) VALUES ('external', 'MYSQL', '1.1.1.1', '5432', 'public', '', 'admin', '12345');

INSERT INTO datasets (name, sql_query, user_id, created_on, updated_on, source_id) VALUES ('User list no param', 'SELECT * FROM users;', 4, current_date, current_date, 1);
INSERT INTO datasets (name, sql_query, user_id, created_on, updated_on, source_id) VALUES ('User list with params', 'SELECT * FROM users WHERE firstname = {:p_name};', 4, current_date, current_date, 1);
INSERT INTO datasets (name, sql_query, user_id, created_on, updated_on, source_id) VALUES ('Very very veryyyyyyyyyy long name','SELECT * FROM customers;', 4, current_date, current_date, 1);

INSERT INTO columns (dataset_id, name, type) VALUES (1, 'id','Double');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'created_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'updated_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'firstname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'lastname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'username','String');

INSERT INTO parameters (dataset_id, name, type, sql_query) VALUES (2,'p_name','String','SELECT distinct firstname FROM users;');

INSERT INTO columns (dataset_id, name, type) VALUES (2, 'id','Double');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'created_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'updated_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'firstname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'lastname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'username','String');

INSERT INTO charts (name, chart_type, user_id, dataset_id, chart_settings) VALUES ('Табличная диаграмма', 'TABLE', 1, 1, '{"xColumns": ["id", "username", "lastname", "firstname"],"where": "id>1", "orderBy": ["lastname"]}'::json);

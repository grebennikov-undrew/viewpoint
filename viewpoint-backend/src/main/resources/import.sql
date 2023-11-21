-- password is the same as username
INSERT INTO users (username, firstname, lastname, email, password, is_active) VALUES ('jdog', 'Jorel', 'Decker', 'jdog@example.com', '$2y$10$ECiw.NXvbbabZmzfx9nBe.9SfFJy9mxOAtpUPMKTe0I6.F70CeBZy', true);
INSERT INTO users (username, firstname, lastname, email, password, is_active) VALUES ('funnyman', 'Dylan', 'Alvarez', 'funnyman@example.com', '$2y$10$YRIsc64l3WRCLO54SZw0yODyh1ZAiZ70lqmcGlaIUq5ggH6FQqXE.', true);
INSERT INTO users (username, firstname, lastname, email, password, is_active) VALUES ('charliescene', 'Jordon', 'Terrell', 'charliescene@example.com', '$2y$10$rTVKGWe9vhxm07K7.9wCsOKp8dkGSzmdhOQ2k/56yBRKQMGxtDQW2', false);
INSERT INTO users (username, firstname, lastname, email, password, is_active) VALUES ('danny', 'Daniel', 'Murillo', 'danny@example.com', '$2y$10$hLOSMYt.eZbKcahKGDJn9u0bpaqElt6NXKVqTaS8LW2H1lBZWWx1u', true);
INSERT INTO users (username, firstname, lastname, email, password, is_active) VALUES ('johnny3tears', 'George', 'Ragan', 'johnny3tears@example.com', '$2y$10$mMA2/vpavfUlg8YD6PSkwuyORLWhbim4kzZiNBCO0VYEf5o1v0bvu', true);

INSERT INTO sources (name, type, netloc, port, dbname, params, username, password) VALUES ('default', 'POSTGRESQL', 'localhost', '5433', 'viewpoint', '', 'postgres', 'postgres');
INSERT INTO sources (name, type, netloc, port, dbname, params, username, password) VALUES ('external', 'MYSQL', '1.1.1.1', '5432', 'public', '', 'admin', '12345');

INSERT INTO datasets (name, sql_query, user_id, source_id) VALUES ('User list no param', 'SELECT * FROM users;', 4, 1);
INSERT INTO datasets (name, sql_query, user_id, source_id) VALUES ('User list with params', 'SELECT * FROM users;', 4, 1);
INSERT INTO datasets (name, sql_query, user_id, source_id) VALUES ('Very very veryyyyyyyyyy long name','SELECT * FROM customers;', 4, 1);

INSERT INTO columns (dataset_id, name, type) VALUES (1, 'id','Double');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'created_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'updated_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'firstname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'lastname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'username','String');
INSERT INTO columns (dataset_id, name, type) VALUES (1, 'role','String');

INSERT INTO parameters (dataset_id, name, type, sql_query) VALUES (2,'p_name','String','SELECT distinct firstname FROM users;');

INSERT INTO columns (dataset_id, name, type) VALUES (2, 'id','Double');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'created_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'updated_on','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'firstname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'lastname','String');
INSERT INTO columns (dataset_id, name, type) VALUES (2, 'username','String');

INSERT INTO charts (name, chart_type, user_id, dataset_id, chart_settings) VALUES ('Табличная диаграмма', 'TABLE', 1, 1, '{"dimensions": [{"value": "id", "label": "id"}, {"value": "username", "label": "username"}, {"value": "lastname", "label": "lastname"}, {"value": "firstname", "label": "firstname"}],"where": "id>1", "orderBy": [{"value": "lastname", "label": "lastname"}]}'::json);
INSERT INTO charts (name, chart_type, user_id, dataset_id, chart_settings) VALUES ('Круговая диаграмма', 'PIE', 1, 2, '{"metrics": [{"value": "role", "label": "COUNT(role)", "aggFunction": "COUNT" }],"where": "id>1", "dimensions": [{"value": "role", "label": "role"}]}'::json);

--  Тестовые данные ---------------------------
DROP TABLE IF EXISTS sales;
CREATE TABLE sales (product_name VARCHAR(50),sale_date DATE,quantity INT,unit_price DECIMAL(10, 2),shop VARCHAR(50));

INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-01', 10, 15.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-01', 8, 20.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-01', 5, 10.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-02', 3, 15.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-02', 2, 20.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-02', 5, 10.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-03', 6, 15.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-03', 3, 20.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-03', 2, 10.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-04', 8, 15.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-04', 7, 20.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-04', 2, 10.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-05', 9, 15.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-05', 2, 20.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-05', 12, 10.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-06', 2, 15.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-06', 2, 20.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-06', 12, 10.00, 'Shop 1');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-01', 2, 15.00, 'Shop 2');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-01', 6, 20.00, 'Shop 2');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-01', 16, 10.00, 'Shop 2');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product A', '2023-09-02', 15, 15.00, 'Shop 2');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product B', '2023-09-02', 20, 20.00, 'Shop 2');
INSERT INTO sales (product_name, sale_date, quantity, unit_price, shop) VALUES ('Product C', '2023-09-02', 1, 10.00, 'Shop 2');

INSERT INTO datasets (name, sql_query, user_id, source_id) VALUES ('Sales','SELECT *, quantity::DECIMAL * unit_price as total_amount FROM sales;', 4, 1);

INSERT INTO columns (dataset_id, name, type) VALUES (4, 'quantity','Double');
INSERT INTO columns (dataset_id, name, type) VALUES (4, 'unit_price','Double');
INSERT INTO columns (dataset_id, name, type) VALUES (4, 'sale_date','Timestamp');
INSERT INTO columns (dataset_id, name, type) VALUES (4, 'product_name','String');
INSERT INTO columns (dataset_id, name, type) VALUES (4, 'shop','String');
INSERT INTO columns (dataset_id, name, type) VALUES (4, 'total_amount','Double');

INSERT INTO charts (name, chart_type, user_id, dataset_id, chart_settings) VALUES ('Продажи за неделю', 'LINE', 1, 4, '{"dimensions": [{"value": "product_name", "label": "product_name"}], "metrics": [{"value" : "quantity", "label": "SUM(quantity)", "aggFunction": "SUM"}], "xAxis": "sale_date", "xAxisType": "Timestamp"}'::json);
INSERT INTO charts (name, chart_type, user_id, dataset_id, chart_settings) VALUES ('Доходы от продуктов по магазинам', 'BAR', 1, 4, '{"dimensions": [{"value": "shop", "label": "shop"}], "metrics": [{"value" : "total_amount", "label": "SUM(total_amount)", "aggFunction": "SUM"}], "xAxis": "product_name", "xAxisType": "String"}'::json);

INSERT INTO charts (name, chart_type, user_id, dataset_id, chart_settings) VALUES ('Доходы от продуктов', 'BAR', 1, 4, '{"dimensions": [], "metrics": [{"value" : "total_amount", "label": "SUM(total_amount)", "aggFunction": "SUM"}], "xAxis": "product_name", "xAxisType": "String"}'::json);

INSERT INTO dashboards (name, user_id, layout) VALUES ('Отчет по продажам', 1, '{"position": [{"w": 6, "h": 2, "x": 0, "y": 0, "i": "3"}, {"w": 6, "h": 2, "x": 7, "y": 0, "i": "4"}], "filters": []}'::json)

INSERT INTO dashboard_chart (dashboard_id, chart_id) VALUES (1, 3);
INSERT INTO dashboard_chart (dashboard_id, chart_id) VALUES (1, 4);
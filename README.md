# Viewpoint - BI System
## Описание
Проект представляет собой Business Intelligence систему, предназначенную для визуализации данных из различных источников.

### Возможности
* Подключение нескольких источников данных (баз данных)
* Редактор SQL для запросов к источникам
* Визуализация данных при помощи таблиц и диаграмм (столбчатые, круговые, линейные)
* Построение дашбордов и применение фильтрации
* Авторизация и хранение каталога пользователей
* Разделение доступов к каталогам по ролям и привилегиям
* Экспорт данных из диаграмм в формате csv

-----------
<details><summary><b>СКРИНШОТЫ</b></summary>
<img src="./pictures/dashboard.jpg" alt="Дашборд" style="max-width:600px;max-height:500px;">

*Дашборд*

<img src="./pictures/pie-editor.jpg" alt="Редактор диаграмм" style="max-width:600px;max-height:500px;">

*Редактор диаграмм*

<img src="./pictures/ds-editor.jpg" alt="Редактор наборов данных" style="max-width:600px;max-height:500px;">

*Редактор наборов данных*

<img src="./pictures/source-editor.jpg" alt="Создание источника данных" style="max-width:600px;max-height:500px;">

*Источники данных*

<img src="./pictures/user-editor.jpg" alt="Дашборд" style="max-width:700px;max-height:700px;">

*Редактор пользователя*
</details>


-----------

### Технологии
* **Framework**: Spring Boot 3 *(Security 6, Data JPA, Validation)*
* **Build**: Maven
* **ORM**: Hibernate
* **DB**: postgres
* **Containers**: Docker, Docker-compose
* **Mapping**: Mapstuct
* **Swagger**: springdoc openapi
* **Front**: React.js, Material UI
* **Other**: Lombok

### Структура проекта
* viewpoint-backend - **Серверное приложение (Spring boot 3)**
* viewpoint-frontend - **Клиентское приложение (React.js)**
* docker - Дополнительные настройки контейнеров Docker

### Пакеты серверного приложения (package-by-feature):
**Chart** - Наполнение диаграмм, стратегии трансформации данных <br>
**Dashboard** - Построение диаграмм и применение фильтров на дашбордах <br>
**Datasets** - Выполнение SQL-запросов на источниках данных <br>
**Security** - Конфигурация Spring Security (авторизация, роли, привилегии) <br>
**Sources** - Валидация и подключение к сторонним источникам данных <br>
**Users** - Логика работы с информацией о пользователях<br>
**Utils** - Вспомогательные классы (SqlBuilder, CsvFormatter...) <br>

-----------
<details><summary><b>SWAGGER</b></summary>
<img src="./pictures/swagger.jpg" alt="Дашборд" style="max-width:650px">
</details>
</details>

-----------

## Инструкция для запуска
    git clone https://github.com/grebennikov-undrew/viewpoint.git
    cd viewpoint
    docker-compose up

http://localhost:3000/ - клиент, для входа использовать admin/admin <br>
http://localhost:8081/ - серверная часть <br>
http://localhost:8081/swagger-ui/index.html#/ - доккументация API
-- Создание схемы
CREATE SCHEMA IF NOT EXISTS catalogue;

-- Создание таблицы
CREATE TABLE IF NOT EXISTS catalogue.t_product
(
    id        SERIAL PRIMARY KEY,
    c_title   VARCHAR(50) NOT NULL CHECK (length(trim(c_title)) >= 3),
    c_details VARCHAR(1000)
);

-- Вставка тестовых данных
INSERT INTO catalogue.t_product(id, c_title, c_details)
VALUES 
    (1, 'Товар №1', 'Описание товара №1'),
    (2, 'Шоколад', 'Горький шоколад'),
    (3, 'Товар №3', 'Описание товара №3'),
    (4, 'Молоко', 'Жирность 3.2%')


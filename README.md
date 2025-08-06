# 🏪 Selmag - Система управления товарами

Современная система управления товарами с микросервисной архитектурой, построенная на Spring Boot 3.5.3 и Java 21.

## 🏗️ Архитектура

Проект состоит из двух основных модулей:

### 📦 Catalogue Service
**REST API сервис** для управления товарами
- **Технологии**: Spring Boot Web, Spring Data JPA, PostgreSQL, Liquibase
- **Порт**: 8080 (по умолчанию)
- **Функции**: CRUD операции с товарами, поиск, валидация

### 🖥️ Manager App
**Веб-интерфейс** для управления товарами
- **Технологии**: Spring Boot Thymeleaf, Spring Boot Web
- **Порт**: 8081 (по умолчанию)
- **Функции**: Веб-интерфейс для работы с товарами

## 🚀 Быстрый старт

### Предварительные требования
- Java 21+
- Maven 3.6+
- PostgreSQL 12+

### 1. Настройка базы данных
```sql
CREATE DATABASE selmag_catalogue;
```

### 2. Настройка конфигурации
Создайте файл `catalogue-service/src/main/resources/application-local.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/selmag_catalogue
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: validate
```

### 3. Запуск сервисов

#### Запуск Catalogue Service:
```bash
cd catalogue-service
mvn spring-boot:run
```

#### Запуск Manager App:
```bash
cd manager-app
mvn spring-boot:run
```

### 4. Доступ к приложению
- **API**: http://localhost:8080
- **Веб-интерфейс**: http://localhost:8081

## 📋 API Endpoints

### Catalogue Service API

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/v1/products` | Получить список товаров |
| `GET` | `/api/v1/products/{id}` | Получить товар по ID |
| `POST` | `/api/v1/products` | Создать новый товар |
| `PUT` | `/api/v1/products/{id}` | Обновить товар |
| `DELETE` | `/api/v1/products/{id}` | Удалить товар |

### Примеры запросов

#### Создание товара:
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Молоко",
    "details": "Свежее молоко 3.2%"
  }'
```

#### Получение списка товаров:
```bash
curl http://localhost:8080/api/v1/products
```

## 🛠️ Технологический стек

### Backend
- **Java 21** - основной язык разработки
- **Spring Boot 3.5.3** - фреймворк для создания приложений
- **Spring Data JPA** - работа с базой данных
- **PostgreSQL** - реляционная база данных
- **Liquibase** - управление миграциями БД
- **Lombok** - уменьшение boilerplate кода
- **Spring Validation** - валидация данных

### Frontend
- **Thymeleaf** - серверный шаблонизатор
- **HTML5/CSS3** - разметка и стили
- **JavaScript** - интерактивность

### Инструменты разработки
- **Maven** - система сборки
- **Spring Boot DevTools** - автоматическая перезагрузка
- **IntelliJ IDEA** - IDE (рекомендуется)

## 📁 Структура проекта

```
selmag-parent-sc24/
├── catalogue-service/          # REST API сервис
│   ├── src/main/java/
│   │   └── com/nik/catalogue/
│   │       ├── controller/     # REST контроллеры
│   │       ├── entity/         # JPA сущности
│   │       ├── repository/     # Репозитории
│   │       └── service/        # Бизнес-логика
│   └── src/main/resources/
│       └── db/changelog/       # Миграции БД
├── manager-app/               # Веб-интерфейс
│   ├── src/main/java/
│   │   └── com/nik/manager/
│   │       ├── controller/     # MVC контроллеры
│   │       ├── client/         # REST клиент
│   │       └── config/         # Конфигурация
│   └── src/main/resources/
│       └── templates/          # Thymeleaf шаблоны
└── pom.xml                    # Родительский POM
```

## 🔧 Разработка

### Сборка проекта
```bash
mvn clean install
```

### Запуск тестов
```bash
mvn test
```

### Запуск в режиме разработки
```bash
# Catalogue Service
cd catalogue-service && mvn spring-boot:run

# Manager App (в другом терминале)
cd manager-app && mvn spring-boot:run
```

## 📊 Модель данных

### Product (Товар)
- `id` - уникальный идентификатор
- `title` - название товара (3-50 символов)
- `details` - описание товара (до 1000 символов)

## 🎯 Функциональность

### Catalogue Service
- ✅ CRUD операции с товарами
- ✅ Поиск товаров по названию
- ✅ Валидация данных
- ✅ REST API
- ✅ Автоматические миграции БД

### Manager App
- ✅ Веб-интерфейс для управления товарами
- ✅ Список товаров с поиском
- ✅ Создание новых товаров
- ✅ Редактирование товаров
- ✅ Просмотр деталей товара

## 🚀 Планы развития

- [x] Аутентификация и авторизация
- [ ] Категории товаров
- [ ] Изображения товаров
- [ ] API документация (Swagger)
- [ ] Docker контейнеризация
- [ ] CI/CD pipeline
- [ ] Мониторинг и логирование

## 🤝 Вклад в проект

1. Fork репозитория
2. Создайте feature branch (`git checkout -b feature/amazing-feature`)
3. Commit изменения (`git commit -m 'Add amazing feature'`)
4. Push в branch (`git push origin feature/amazing-feature`)
5. Откройте Pull Request

## 👨‍💻 Автор

**Nikita** - [GitHub](https://github.com/NikitaMorozka)

---

⭐ Если проект вам понравился, поставьте звездочку! 
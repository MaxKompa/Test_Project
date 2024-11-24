Test_Project — REST API для управления данными профиля пользователя. Проект разработан на Kotlin с использованием Ktor, SQLite и ORM Exposed.

Функционал

1. Получение данных профиля

Endpoint: GET /profile

Возвращает JSON с данными профиля пользователя:

{
  "name": "Ivan",
  "surname": "Ivanov",
  "jobTitle": "Developer",
  "phone": "+79999999999",
  "address": "Moscow",
  "interests": ["Startups", "Tech"],
  "isPublic": true,
  "avatarUrl": "https://example.com/avatar.jpg"
}

2. Обновление данных профиля

Endpoint: PUT /profile

Принимает JSON с новыми данными профиля и обновляет их в базе данных.
Пример тела запроса:

{
  "name": "John",
  "surname": "Doe",
  "jobTitle": "Engineer",
  "phone": "+71234567890",
  "address": "New York",
  "interests": ["Reading", "Gaming"],
  "isPublic": false
}

Возвращает статус 200 OK с обновленными данными профиля.

3. Загрузка аватара

Endpoint: POST /upload-avatar

Принимает изображение через multipart/form-data, сохраняет файл локально и возвращает URL загруженного аватара.
Пример ответа:

{
  "avatarUrl": "http://localhost:8080/avatars/<filename>.jpg"
}

Валидация данных профиля

Name: От 2 до 50 символов, только буквы и пробелы.

Surname: От 2 до 50 символов, только буквы и пробелы.

Job Title: До 100 символов, буквы, цифры и пробелы.

Phone: Формат +<код страны><номер> (например, +79999999999).

Address: До 200 символов, буквы, цифры, запятые, точки, дефисы, пробелы.

Interests: Максимум 10 тегов, каждый тег до 30 символов.

Avatar: Форматы .jpg, .jpeg, .png, размер до 5 MB.

isPublic: Либо true, либо false.

Установка и запуск

1. Клонирование репозитория

git clone https://github.com/username/profile-api.git
cd profile-api

2. Сборка и запуск проекта

Убедитесь, что у вас установлены следующие инструменты:

Java 11+

Maven

Сборка проекта

mvn clean install

Запуск сервера

mvn exec:java -Dexec.mainClass=com.example.ApplicationKt

Сервер запустится по адресу: http://localhost:8080

Структура проекта

profile-api/
├── src/main/kotlin
│   ├── com/example/
│   │   ├── Application.kt      # Основная точка входа
│   │   ├── Database.kt         # Конфигурация базы данных
│   │   ├── Models.kt           # Определение моделей
│   │   └── Routing.kt          # Роутинг и обработчики запросов
├── avatars/                    # Папка для загрузки аватаров
├── src/main/resources
│   └── application.conf        # Конфигурация Ktor
├── pom.xml                     # Конфигурация Maven
└── README.md                   # Документация

База данных

Таблица profiles

Используется база данных SQLite. Таблица profiles хранит данные профиля:

CREATE TABLE profiles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    job_title TEXT,
    phone TEXT NOT NULL UNIQUE,
    address TEXT,
    interests TEXT,
    is_public BOOLEAN DEFAULT FALSE,
    avatar_url TEXT
);

Инициализация базы данных

Если файл базы данных отсутствует, он будет создан автоматически при запуске приложения.

Дополнительно

Документация API: Используйте инструменты, такие как Postman, для тестирования запросов.

Логирование: Логи сохраняются в консоли для удобства отладки.

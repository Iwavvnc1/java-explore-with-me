# Исследуй со мной

Исследуй со мной - это сервис, который позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них. Сервис состоит из двух модулей: основного и статистического.

## Технологии

Сервис разработан с использованием следующих технологий:

- Java 11
- Spring Boot
- Lombok
- Hibernate
- JPA
- PostgreSQL
- H2

## Основной модуль

Основной модуль содержит всё необходимое для работы продукта, такое как:

- Модели данных для событий, пользователей, комментариев и т.д.
- Репозитории для доступа к базе данных
- Сервисы для бизнес-логики
- Контроллеры для обработки запросов
- Конфигурации для подключения к базе данных и другим ресурсам
- Тесты для проверки корректности работы приложения

API основного сервиса разделено на три части:

- Публичная - будет доступна без регистрации любому пользователю сети. Она позволяет просматривать список событий, фильтровать их по категориям, датам и местоположению, а также просматривать детали конкретного события.
- Закрытая - будет доступна только авторизованным пользователям. Она позволяет создавать, редактировать и удалять свои события, подписываться на события других пользователей, оставлять комментарии и оценки, а также просматривать свой профиль и настройки.
- Административная - для администраторов сервиса. Она позволяет управлять всеми сущностями в базе данных, просматривать статистику по сервису, а также блокировать и разблокировать пользователей.

## Модуль статистики

Модуль статистики хранит количество просмотров и позволяет делать различные выборки для анализа работы приложения. Он использует отдельную базу данных H2 для хранения статистических данных. Он также предоставляет API для получения статистики по разным параметрам.

## Эндпоинты сервиса
### Эндпоинты административного доступа:  

| Класс | Метод | Путь | Описание |
| --- | --- | --- | --- |
| AdminCategoriesController | POST | /admin/categories | Создать новую категорию |
| AdminCategoriesController | DELETE | /admin/categories/{catId} | Удалить категорию по идентификатору |
| AdminCategoriesController | PATCH | /admin/categories/{catId} | Обновить категорию по идентификатору |
| AdminCommentController | DELETE | /admin/comments/{commId} | Удалить комментарий по идентификатору |
| AdminCommentController | GET | /admin/comments/users/{userId} | Получить список всех комментариев пользователя по идентификатору |
| AdminCommentController | GET | /admin/comments/events/{eventId} | Получить список всех комментариев события по идентификатору |
| AdminCompilationsController | POST | /admin/compilations | Создать новую подборку |
| AdminCompilationsController | DELETE | /admin/compilations/{compId} | Удалить подборку по идентификатору |
| AdminCompilationsController | PATCH | /admin/compilations/{compId} | Обновить подборку по идентификатору |
| AdminEventsController | GET | /admin/events | Получить список событий по заданным параметрам |
| AdminEventsController | PATCH | /admin/events/{eventId} | Обновить событие по идентификатору |
| AdminUserController | POST | /admin/users | Создать нового пользователя |
| AdminUserController | DELETE | /admin/users/{userId} | Удалить пользователя по идентификатору |
| AdminUserController | GET | /admin/users?ids=&from=&size=  | Получить список пользователей с определенными параметрами |

### Эндпоинты закрытого доступа:  
| Класс | Метод | Путь | Описание |
| --- | --- | --- | --- |
| PrivateCommentController | POST | /users/{userId}/comments/events/{eventId} | Создать новый комментарий к событию |
| PrivateCommentController | PUT | /users/{userId}/comments/{commId}/like | Добавить лайк к комментарию |
| PrivateCommentController | DELETE | /users/{userId}/comments/{commId} | Удалить комментарий по идентификатору |
| PrivateCommentController | PATCH | /users/{userId}/comments/{commId} | Обновить комментарий по идентификатору |
| PrivateCommentController | GET | /users/{userId}/comments | Получить список всех комментариев пользователя по идентификатору |
| PrivateRequestController | GET | /users/{userId}/requests | Получить список всех заявок на участие в событиях пользователя по идентификатору |
| PrivateRequestController | POST | /users/{userId}/requests?eventId={eventId} | Создать новую заявку на участие в событии |
| PrivateRequestController | PATCH | /users/{userId}/{requestId}/cancel | Отмена своего запроса на участие в событии |
| PrivateEventsController | POST | /users/{userId}/events | Создает новое событие для пользователя с указанным userId |
| PrivateEventsController | GET | /users/{userId}/events | Возвращает список событий текущего пользователя с указанным userId |
| PrivateEventsController | GET | /users/{userId}/events/{eventId} | Возвращает информацию о событии с указанным eventId для пользователя с указанным userId |
| PrivateEventsController | GET | /users/{userId}/events/{eventId}/requests | Возвращает список заявок на участие в событии с указанным eventId для пользователя с указанным userId |
| PrivateEventsController | PATCH | /users/{userId}/events/{eventId} | Обновляет информацию о событии с указанным eventId для пользователя с указанным userId |
| PrivateEventsController | PATCH | /users/{userId}/events/{eventId}/requests | Обновляет статус заявок на участие в событии с указанным eventId для пользователя с указанным userId |

### Эндпоинты публичного доступа:  
| Класс | Метод | Путь | Описание |
| --- | --- | --- | --- |
| PublicCategoryController | GET | /categories | Возвращает список категорий событий |
| PublicCategoryController | GET | /categories/{catId} | Возвращает информацию о категории события с указанным catId |
| PublicCompilationController | GET | /compilations | Возвращает список подборок событий |
| PublicCompilationController | GET | /compilations/{compId} | Возвращает информацию о подборке событий с указанным compId |
| PublicEventsController | GET | /events | Возвращает список событий, соответствующих заданным параметрам |
| PublicEventsController | GET | /events/{id} | Возвращает информацию о событии с указанным id |

## Лицензия

Сервис распространяется под лицензией MIT. Вы можете свободно использовать, изменять и распространять его при условии указания авторства и ссылки на исходный код.

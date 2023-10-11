### Эндпоинты доступа администратора:  

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

### Эндпоинты доступа авторизованного пользователя:  
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

### Эндпоинты доступа не авторизованного пользователя:  
| Класс | Метод | Путь | Описание |
| --- | --- | --- | --- |
| PublicCategoryController | GET | /categories | Возвращает список категорий событий |
| PublicCategoryController | GET | /categories/{catId} | Возвращает информацию о категории события с указанным catId |
| PublicCompilationController | GET | /compilations | Возвращает список подборок событий |
| PublicCompilationController | GET | /compilations/{compId} | Возвращает информацию о подборке событий с указанным compId |
| PublicEventsController | GET | /events | Возвращает список событий, соответствующих заданным параметрам |
| PublicEventsController | GET | /events/{id} | Возвращает информацию о событии с указанным id |

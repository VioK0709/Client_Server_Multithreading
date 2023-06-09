# Курсовой проект "Сетевой чат"

## [Server](https://github.com/VioK0709/Client_Server_Multithreading/tree/main/Server) - [Client](https://github.com/VioK0709/Client_Server_Multithreading/tree/main/Client)

Два приложения для обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями. 

**Первое приложение - сервер чата**, ожидает подключения пользователей.

**Второе приложение - клиент чата**, подключается к серверу чата и осуществляет доставку и получение новых сообщений.

Все сообщения записываются в .log как на сервере, так и на клиентах. .log дополняется при каждом запуске, а также при отправленном или полученном сообщении. Выход из чата осуществляется по команде “stop”.

## Реализация требований к серверу

- Установка порта для подключения клиентов через файл настроек settings.json;
- Возможность подключиться к серверу в любой момент и присоединиться к чату;
- Отправка новых сообщений клиентам;
- Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки.

## Реализация требований к клиенту

- Выбор имени для участия в чате;
- Установка порта для подключения клиентов через файл настроек settings.json;
- Подключение к указанному в настройках серверу;
- Для выхода из чата нужно набрать команду выхода - “stop”;
- Каждое сообщение участников записывается в текстовый файл - файл логирования. При каждом запуске приложения файл дополняется.

## Общая реализация

- Сервер умеет одновременно ожидать новых пользователей и обрабатывать поступающие сообщения от пользователей;
- Использован сборщик пакетов maven;
- Код покрыт unit-тестами.

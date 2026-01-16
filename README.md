# MailPilot

MailPilot to webowa aplikacja wykonana w technologii **Java + Spring Boot**. Prezentuja ona:
- architekturę mikroserwisową,
- asynchroniczną komunikację,
- integrację z zewnętrznym **API (Google Gmail)**,
- obsługę poczty elektronicznej **(SMTP + Gmail API)**,
- nowoczesny interfejs webowy.
---
## Funkcjonalności
### 1. Gmail (OAuth2)
- logowanie przez konto Google (OAuth2),
- przeglądanie skrzynki odbiorczej Gmail,
- podgląd maili (HTML + tekst),
- wysyłanie maili przez Gmail API,
- obsługa tokenów dostępu i odświeżania.
### 2. MailHog (SMTP Sandbox)
- testowa wysyłka maili bez użycia prawdziwego maila,
- podgląd wiadomości w MailHog Web UI,
- retry wysyłki nieudanych wiadomości (Outbox Pattern).
### 3. Outbox & Retry
- kolejka wysyłki maili,
- obsługa błędów,
- ponowna próba wysyłki,
- asynchroniczne przetwarzanie.
---
## Technologie
- Java 17
- Spring Boot 3.4.5
- Spring Web (REST API)
- Spring Security (OAuth2 – Google / Gmail)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway
- RabbitMQ (asynchroniczna komunikacja)
- Gmail API
- Thymeleaf
- HTML5 / CSS3 / JavaScript
- Docker
- Maven
---
## Architektura
Projekt wykorzystuje architekturę mikroserwisową:
#### Mikroserwisy:
#### 1. gateway-service
- interfejs webowy (Thymeleaf),
- REST API,
- integracja z Gmail API,
- OAuth2 (Google),
- zarządzanie Outboxem.
#### 2. sync-service
- worker asynchroniczny,
- nasłuchiwanie kolejek RabbitMQ,
- przetwarzanie zadań w tle.
#### Komunikacja:
- HTTP (REST) – komunikacja z UI i API,
- SMTP – wysyłka maili (MailHog / Gmail),
- AMQP (RabbitMQ) – komunikacja asynchroniczna,
- TCP – wszystkie powyższe protokoły.
---
## Baza danych
- PostgreSQL
- Hibernate (JPA)
- Flyway – migracje schematu
---
## Uruchomienie projektu (lokalnie)
#### Wymagania
- Java 17+
- Maven
- Docker + Docker Compose
- Konto Google (do integracji Gmail)
### Szybkie uruchomienie (bez Gmail)
`>docker compose up`
#### Po uruchomieniu:
Wejdź na: `http://localhost:8080`

Ta wersja pozwala:
- wysyłać testowe maile przez SMTP,
- przeglądać Outbox,
- testować retry i asynchroniczną komunikację.
### Konfiguracja Gmail
Każdy użytkownik konfiguruje własną integrację Google Cloud.
1. Wejdź na https://console.cloud.google.com
2. Utwórz nowy projekt
3. Włącz Gmail API
4. Skonfiguruj projekt
```
# OAuth consent screen:
typ: External
tryb: Testing

# OAuth Client ID:
typ: Web application

# Authorized redirect URI:
http://localhost:8080/login/oauth2/code/google

```
5. Ustaw zmienne środowiskowe:
```
GOOGLE_CLIENT_ID=twoj_client_id
GOOGLE_CLIENT_SECRET=twoj_client_secret
```
lub użyj pliku .env (lokalnie):
```
GOOGLE_CLIENT_ID=...
GOOGLE_CLIENT_SECRET=...
```
Sekrety NIE są przechowywane w repozytorium.

6. Logowanie
- Uruchom aplikację
- Wejdź na stronę główną
- Kliknij Zaloguj Google
- Zgódź się na dostęp
- Korzystaj z Gmail Inbox i wysyłki maili
---
## REST API (przykłady)
Outbox:
- `POST /api/outbox/send`
- `GET /api/outbox/mails`
- `POST /api/outbox/mails/{id}/retry`

Gmail:
- `GET /api/gmail/inbox`
- `GET /api/gmail/messages/{id}`
- `POST /api/gmail/send`
---
## Autorzy
[Antoni Gierlach](https://github.com/AntoniGierlach)

Projekt wykonany w ramach projektu programowania sieciowego.

# Building Web Services with Java EE 8 <br>Section 4: Building Asynchronous Web Services

## Videos

### Video 4.1: Benefits and usage scenarios of asynchronous processing

In this video we are going to talk about Content-Types and Content Negotiation.

### Video 4.2: Implementing asynchronous web services

In this video we are showing how to use JSON-B for easy data binding.

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/json-b/ | 200 | Marshall JSON-B annotated POJO |
| POST   | /api/json-b/ | 204 | Unmarshall and update JSON-B annotated POJO |
| GET    | /api/json-b/custom | 200 | Marshall custom POJO using Jsonb |
| POST   | /api/json-b/custom | 204 | Unmarshall custom POJO using Jsonb |


### Video 4.3: Using ManagedExecutorService and Server-side Callbacks

In this video we are showing how to use JSON-P for flexible JSON processing.

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/json-p/ | 200 | Marshall a JsonArray of JsonObject using JSON-P |
| POST   | /api/json-p/ | 204 | Unmarshall and Update a JsonArray of JsonObject using JSON-P |
| PATCH  | /api/json-p/ | 204 | Patch a JsonArray of JsonObject using JSON-P Pointer |


### Video 4.4: Implementing asynchronous web service clients

In this video we are showing how to build hypermedia-driven REST APIs.

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/hateos/books | 200 | Get a list of books |
| GET    | /api/hateos/books?authorId={authorId} | 200 | Get a list of books for given authorId |
| GET    | /api/hateos/books/{isbn} | 200 | Get a book by ISBN |
| GET    | /api/hateos/author | 200 | Get a list of authors |
| GET    | /api/hateos/author/{id} | 200 | Get an author by ID |


## Building and Running

```bash
$ mvn clean verify

$ docker build -t async-service:1.0 .
$ docker run -it -p 8080:8080 async-service:1.0
```

# Building Web Services with Java EE 8 <br>Section 4: Building Asynchronous Web Services

## Videos

### Video 4.1: Benefits and usage scenarios of asynchronous processing

In this video we are talking about the benefits and possible usage scenarios of asynchronous processing.

### Video 4.2: Implementing asynchronous web services

In this video we are implementing simple asynchronous web services.

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/thread | 200 | Returns request and response threads name |
| GET    | /api/async | 200 or 503 | Locks the request thread and returns the response thread name or 503 if timeout |
| DELETE   | /api/async | 204 | Unlocks the previous request thread |

### Video 4.3: Using ManagedExecutorService and Server-side Callbacks

In this video is showing how to use ManagedExecutorService and Server-side Callbacks
to calculate Fibonacci numbers async.

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/fibonacci/{i} | 200 | Returns the Fibonacci for i |

### Video 4.4: Implementing asynchronous web service clients

In this video we are showing how to implement an async REST client.

## Building and Running

```bash
$ mvn clean verify

$ docker build -t async-service:1.0 .
$ docker run -it -p 8080:8080 async-service:1.0
```

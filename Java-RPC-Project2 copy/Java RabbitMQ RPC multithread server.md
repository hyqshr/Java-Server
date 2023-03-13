# Java RabbitMQ RPC multithread server

A multithreaded RPC key-value store server-client application implemented with RabbitMQ.

This project is for NEU CS 6650 project2.

The feature including:

- The server is multithreaded with threadpool, the map is thread safe `ConcurrentHashMap`.
- RabbitMQ rpc call implemented
- The client will send GET, PUT, DELETE operation to server

RabbitMQ use AMQP which is an open protocol enabling messaging between systems. It focus on process-to-process communication through TCP/IP connections.



## To run the program

Start RabbitMQ using brew:

```
brew services start rabbitmq
```



```
cd src
javac -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar *.java
```



And then, start the server:

```
java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar RPCServer
```

start the client, open a new terminal, and then: 

```
cd src
java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar RPCClient
```



Stop RabbitMQ using brew:

```
brew services stop rabbitmq
```



Stop RabbitMQ using brew:

```
brew services stop rabbitmq
```



# Build Project with docker

1. Compule server docker file

```
javac -cp $CP src/RPCServer.java src/RequestHandler.java

```


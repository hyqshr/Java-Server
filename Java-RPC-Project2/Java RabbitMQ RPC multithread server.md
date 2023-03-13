# Java RabbitMQ RPC multithread server

## About this project

A multithreaded RPC key-value store server-client application implemented with RabbitMQ.

This project is for NEU CS 6650 project2.

- The server is multithreaded with `threadpool`, the map is thread safe using `ConcurrentHashMap`.
- RabbitMQ rpc call implemented
- The client will send GET, PUT, DELETE operation to server, each method will be sent 100 times 
- ` slf4j-simple-1.7.36.jar ` ` slf4j-api-1.7.36.jar` ` amqp-client-5.16.0.jar`  is the required *RabbitMQ* support file in `src` folder. They are available to download on rabbitmq official website.  



## RUN

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

it will show: 

```
>>> INFO: System time: 1677451858572- Awaiting RPC requests...
>>> Feb 26, 2023 5:50:58 PM RPCServer main
```



to start the client, open a new terminal, and then: 

```
cd src
java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar RPCClient
```



Stop RabbitMQ using brew:

```
brew services stop rabbitmq
```



## Executive summary

#### assignment overview

An RPC server is responsible for receiving requests from clients, processing them, and sending back responses. When multiple clients send requests simultaneously, a multithreaded RPC server can handle these requests concurrently, improving performance and throughput. A multithreaded RPC server is important to distributed system in terms of Scalability , Responsiveness, Resource utilization, Load balancing. RabbitMQ is a popular open-source message broker that provides a reliable and scalable messaging solution for modern distributed systems



#### technical impression

The challenge in this assignment is: 

- How to make server multithreaded and make the map thread safe?

Java offer out-of-box thread management package. I use threadpool provided by class `ExecutorService`  to minimizes the overhead due to thread creation. 

 I implement `ConcurrentHashMap` in order to make the server map thread safe.

- How to implement RPC with RabbitMQ

RPC in RabbitMQ can be a bit confusing a first since RabbitMQ is known for message queue functionality. RabbitMQ will use its rpc_queue for RPC. When the client starts up, it will create an callback queue. The client will sends message through the rpc_queue. The server will then response through the queue with the message.


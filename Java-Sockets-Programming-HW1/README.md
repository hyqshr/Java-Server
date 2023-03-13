# NEU CS6650 Project1
This project implement a server key-value storage with UDP and TCP in java

To run this project, compile all the file
```java
javac *.java
```

Then:
```java
 java TCPServer.java 9998
```
to start the TCP server.

```java
java TCPClient.java localhost 9998
```
To run the TCP client.

Same for the UDP client:
```java
java UDPServer.java 9998
```
```java
java UDPClient.java 9998
```

The client with send 5 request of each PUT, DEL, GET request to server and modify the key-value map in the server.

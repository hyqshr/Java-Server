FROM bellsoft/liberica-openjdk-alpine-musl:17 AS server-build
WORKDIR /usr/src/myapp
COPY . /usr/src/myapp
RUN export CP=.:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar
#RUN javac -cp $CP src/RPCServer.java src/RequestHandler.java
RUN javac -cp $CP src/*.java

#FROM bellsoft/liberica-openjdk-alpine-musl:17 AS client-build
#WORKDIR /usr/src/myapp
#COPY . /usr/src/myapp
#RUN javac -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar client/RPCClient.java
# cmd to run server locally - java server.ServerApp 1111 5555
# start rabbitmq service

CMD ["brew","services","start","rabbitmq"]
CMD ["cd", "src"]
CMD ["java", "-cp",".:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar","RPCServer"]


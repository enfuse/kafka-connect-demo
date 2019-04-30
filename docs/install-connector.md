# How to Install and Run a Custom Connector

The Confluent Kafka Connect Server comes with binaries that allow you to start a connector in [standalone or distributed mode](https://docs.confluent.io/current/connect/userguide.html#standalone-vs-distributed). 
For testing and development purposes, let's install a standalone connector. These instructions assume you already have Kafka and Kafka Connect clusters running in GKE.


1. Under this project's `/config` directory, you will find sample [randomlong-connect-standalone.properties](../config/randomlong-connect-standalone.properties) and [randomlong-connector.properties](../config/randomlong-connector.properties) files. 
First `cd` into the root of this project. 

    In `randomlong-connect-standalone.properties`, modify the address for your kafka brokers: 
    
    ```properties
    bootstrap.servers=<replace with your kafka ip address>:9092
    ```
    
    In `randomlong-connector.properties`, modify the address for the endpoint you want to hit:
    
    ```properties
    api.url=<replace with your api's ip address>:8080
    ```

2. Copy the two `.properties` files into the `/etc/kafka` directory in your connect server with the following commands:
	
	```bash
	$ kubectl get pods // to see the name of your kafka connect pod
	$ kubectl cp /config/randomlong-connect-standalone.properties <kafka-connect-pod-name>:/etc/kafka -c cp-kafka-connect-server
	$ kubectl cp /config/randomlong-connector.properties <kafka-connect-pod-name>:/etc/kafka -c cp-kafka-connect-server
	```

2. Build an uber-jar with `$ ./gradlew clean shadowJar` 

3. In another terminal window, SSH into the kafka connect server container to create a new directory:
	
	```bash
	$ kubectl exec -ti <kafka-connect-pod-name> -c cp-kafka-connect-server bash
	$ cd /user/share/java
	$ mkdir kafka-connect-randomlong
	```

4. Copy the uber-jar from `/connector/build/libs` into the `/usr/share/java/kafka-connect-randomlong-connector` directory in the kafka connect container:  

	```bash
	$ kubectl cp connector/build/libs/connector-all.jar <kafka-connect-pod-name>:/usr/share/java/kafka-connect-randomlong/ -c cp-kafka-connect-server
	```

5. Go back to the terminal window where you're ssh-ed into the container and run:
	
	```bash
	/usr/bin/connect-standalone /etc/kafka/randomlong-connect-standalone.properties /etc/kafka/randomlong-connector.properties
	```


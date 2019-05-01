# How to Install and Run a Custom Connector

The Confluent Kafka Connect Server comes with binaries that allow you to start a connector in [standalone or distributed mode](https://docs.confluent.io/current/connect/userguide.html#standalone-vs-distributed). 
For testing and development purposes, let's install a standalone connector. These instructions assume you already have Kafka and Kafka Connect clusters running in GKE.

### Standalone mode

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

### Distributed mode

To run the connector in distributed mode:

1. In `randomlong-connect-distributed.properties`, modify the address for your kafka brokers: 
       
       ```properties
       bootstrap.servers=<replace with your kafka brokers ip address>:9092
       ```
 
2. Copy the [randomlong-connect-distributed.properties](../config/randomlong-connect-distributed.properties) file 
into the `etc/kafka` directory in your kafka connect server with the following command: 

	```bash
	$ kubectl get pods // to see the name of your kafka connect pod
	$ kubectl cp /config/randomlong-connect-distributed.properties <kafka-connect-pod-name>:/etc/kafka -c cp-kafka-connect-server
	```

3. As before, you will need your connector uber-jar in the `/user/share/java/kafka-connect-randomlong` directory of the kafka connect container. 
See above step #4 from previous section.

4. `kubectl exec` into the kafka connect container and run:
   	
   	```bash
   	/usr/bin/connect-distributed /etc/kafka/randomlong-connect-distributed.properties
   	```

    > Note that `/usr/bin/connect-distributed` only takes the configuration properties for the connect workers. 
    Configuration for your custom connector will be passed through the Kafka Connect REST API, which we'll do in the next step.
    
5. Set up port-forwarding to the rest port for your custom connector:

    ```bash
    $ kubectl port-forward <kafka-connect-pod-name> 8085:8085
    ```
   
   > See the `rest.port` property in `randomlong-connect-distributed.properties` to see which port to use. 

6. Submit a POST request to the Kafka Connect REST API to create your new connector, passing in the required configuration properties through the request body: 

    ```bash
    curl -X POST \
      http://localhost:8086/connectors \
      -H 'Accept: */*' \
      -H 'Content-Type: application/json' \
      -H 'Host: localhost:8086' \
      -d '{
        "name": "randomlong_source_connector",
        "config": {
            "connector.class": "io.enfuse.kafka.connect.connector.RandomLongSourceConnector",
            "api.url": "35.224.207.20:8080",
            "topic": "randomlong_distributed_topic",
            "sleep.seconds": 5
        }
    }'
    
    ```
    
    > Don't forget to modify the value for `api.url` in your request body!
# Example Kafka Source Connector
This project contains the source code for a custom API and Source Connector. 
The RandomLong API has a single endpoint that provides a random Long value. 
The Source Connector periodically calls the endpoint to produce the random Long value to a Kafka topic. 

## Getting Started
Clone repo:
```bash
git clone git@github.com:enfuse/kafka-connect.git
```

Compile and package docker image:
```bash
$ cd kafka-connect
$ ./gradlew clean build docker
```

Create Source Connector uber-jar:
```bash
$ cd kafka-connect
$ ./gradlew clean shadowJar
```

* [Use Google Kubernetes Engine](docs/gcloud.md)
* [Use Minikube On Local Machine](docs/minikube-setup.md)
* [How to Install and Run a Custom Connector](docs/install-connector.md)
* [Helpful Links](docs/links.md)
* [Helpful Kafka Commands](docs/kafka.md)
* [Push API Image to Google Registry and Deploy on GKE](docs/deploy-api-to-google-registry.md)

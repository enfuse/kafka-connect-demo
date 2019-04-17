# kafka-connect
Kafka Connect Sandboxing

### Prerequisites
Virtualbox (default VM used by minikube):
```bash
brew cask install virtualbox
```

Minikube (simple Kubernetes setup for local, singe-node cluster):
```bash
brew cask install minikube
```

### Getting Started
Clone repo:
```bash
git clone git@github.com:davepmiller/kafka-connect.git
```

#### Using GKE Cloud
* [gcloud setup](docs/gcloud-setup.md)


#### Using local setup with minikube:
* [Reference for using local image](https://blogmilind.wordpress.com/2018/01/30/running-local-docker-images-in-kubernetes/)
This might take awhile
```bash
$ minikube start
$ eval $(minikube docker-env)
```

#### Build project and docker image:
```bash
$ cd kafka-connect
$ ./gradlew build docker
```

#### Deploy application:
```bash
$ kubectl run kafka-connect --image=com.enfuse.kafka.connect/kafka-connect --image-pull-policy=Never --port=8080
```

#### Verify:
```bash
$ kubectl get pods
```

#### Expose application locally:
```bash
$ kubectl expose deployment kafka-connect --type=NodePort
$ minikube service kafka-connect
```

#### Clean deployment:
```bash
$ kubectl delete service kafka-connect
$ kubectl delete deployment kafka-connect
```
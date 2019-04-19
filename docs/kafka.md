# Helpful Kafka Commands

### Produce Topics
```bash
$ kubectl exec -c cp-kafka-broker -it confluent-oss-cp-kafka-0 -- /bin/bash /usr/bin/kafka-console-producer --broker-list localhost:9092 --topic test
```
Wait for `>` prompt and type some messages

### Consume Topics
```bash
$ kubectl exec -c cp-kafka-broker -it confluent-oss-cp-kafka-0 -- /bin/bash  /usr/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning
```

### Use Kafka Connect REST API To Start A Connector
This connector sources the postgresql database
```bash
$ curl -d @"connect-postgresql-source.json" -H "Content-Type: application/json" -X POST http://localhost:8080/connectors
```


### Consume with [REST API](https://github.com/confluentinc/kafka-rest)
Expose cp-kafka-rest to your local machine:
```bash
$ gcloud container clusters get-credentials enfuse-kafka-connect-gke --zone us-central1-a --project enfuse-gke \
  >  && kubectl port-forward $(kubectl get pod --selector="app=cp-kafka-rest,release=confluent-oss" --output jsonpath='{.items[0].metadata.name}') 8081:8082
```

Create consumer:
```bash
$ curl -X POST -H "Content-Type: application/vnd.kafka.v2+json" -H "Accept: application/vnd.kafka.v2+json" \
    --data '{"name": "postgres-consumer-instance", "format": "json", "auto.offset.reset": "earliest"}' \
    http://localhost:8081/consumers/my_json_consumer
```
Subscribe consumer to topic:
```bash
$ curl -X POST -H "Content-Type: application/vnd.kafka.v2+json" --data '{"topics":["postgres-data"]}' \
    http://localhost:8081/consumers/my_json_consumer/instances/postgres-consumer-instance/subscription
```
Consume data from the topic:
```bash
$ curl -X GET -H "Accept: application/vnd.kafka.json.v2+json" \
    http://localhost:8081/consumers/my_json_consumer/instances/postgres-consumer-instance/records
```
Delete consumer:
```bash

```
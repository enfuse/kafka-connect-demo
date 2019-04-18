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

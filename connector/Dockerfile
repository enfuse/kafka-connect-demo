FROM confluentinc/cp-kafka-connect-base:5.2.1

ENV CONNECT_GROUP_ID connect-randomlong-cluster
ENV CONNECT_PLUGIN_PATH /usr/share/java/kafka-connect-randomlong
ENV CONNECT_BOOTSTRAP_SERVERS PLAINTEXT://enfuse-cp-kafka-headless:9092
ENV CONNECT_REST_ADVERTISED_HOST_NAME (v1:status.podIP)

ENV CONNECT_INTERNAL_KEY_CONVERTER org.apache.kafka.connect.json.JsonConverter
ENV CONNECT_INTERNAL_VALUE_CONVERTER org.apache.kafka.connect.json.JsonConverter
ENV CONNECT_KEY_CONVERTER org.apache.kafka.connect.storage.StringConverter
ENV CONNECT_VALUE_CONVERTER org.apache.kafka.connect.storage.StringConverter
ENV CONNECT_KEY_CONVERTER_SCHEMAS_ENABLE true
ENV CONNECT_VALUE_CONVERTER_SCHEMAS_ENABLE true

ENV CONNECT_OFFSET_STORAGE_TOPIC connect-randomlong-offsets
ENV CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR 1
ENV CONNECT_OFFSET_FLUSH_INTERVAL_MS 10000
ENV CONNECT_CONFIG_STORAGE_TOPIC connect-randomlong-configs
ENV CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR 1
ENV CONNECT_STATUS_STORAGE_TOPIC connect-randomlong-status
ENV CONNECT_STATUS_STORAGE_REPLICATION_FACTOR 1

ENV KAFKA_JMX_PORT 5555

COPY build/libs/connector-all.jar /usr/share/java/kafka-connect-randomlong/connector-all.jar
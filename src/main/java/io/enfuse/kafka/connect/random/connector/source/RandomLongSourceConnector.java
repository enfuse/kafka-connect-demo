package io.enfuse.kafka.connect.random.connector.source;

import java.util.*;

import io.enfuse.kafka.connect.random.connector.source.config.RandomLongSourceConnectorConfig;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import static io.enfuse.kafka.connect.random.connector.source.config.RandomLongSourceConnectorConfig.API_ENDPOINT;

public class RandomLongSourceConnector extends SourceConnector {
    public static final String VERSION = "0.1.0";

    private RandomLongSourceConnectorConfig randomLongSourceConnectorConfig;

    @Override
    public void start(Map<String, String> props) {
        randomLongSourceConnectorConfig = new RandomLongSourceConnectorConfig(props);
    }

    @Override
    public void stop() {
        // No resources to release, nothing to do here
    }

    @Override
    public Class<? extends Task> taskClass() {
        return RandomLongSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        Map<String, String> config = randomLongSourceConnectorConfig.originalsStrings();
        config.put(API_ENDPOINT, randomLongSourceConnectorConfig.getUrl());
        return Collections.singletonList(config);
    }

    @Override
    public String version() {
        return VERSION;
    }

}

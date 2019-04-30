package io.enfuse.kafka.connect.connector;

import io.enfuse.kafka.connect.connector.config.RandomLongSourceConnectorConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import static io.enfuse.kafka.connect.connector.config.RandomLongSourceConnectorConfig.API_URL_CONFIG;

public class RandomLongSourceConnector extends SourceConnector {
    public static final String VERSION = "0.1.0";

    private RandomLongSourceConnectorConfig randomLongSourceConnectorConfig;

    @Override
    public void start(Map<String, String> props) {
        randomLongSourceConnectorConfig = new RandomLongSourceConnectorConfig(props);
    }

    @Override
    public void stop() {}

    @Override
    public Class<? extends Task> taskClass() {
        return RandomLongSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        Map<String, String> config = randomLongSourceConnectorConfig.originalsStrings();
        config.put(API_URL_CONFIG, randomLongSourceConnectorConfig.getUrl());
        return Collections.singletonList(config);
    }

    public ConfigDef config() {
        return RandomLongSourceConnectorConfig.config();
    }

    @Override
    public String version() {
        return VERSION;
    }

}

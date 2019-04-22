package io.enfuse.kafka.connect.random.connector.source.config;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.List;
import java.util.Map;

import static org.apache.kafka.common.config.ConfigDef.NO_DEFAULT_VALUE;

public class RandomLongSourceConnectorConfig extends AbstractConfig {

    public static final String API_ENDPOINT = "api.endpoint";
    private static final String API_ENDPOINT_DOC = "API URL";

    public static final String TOPIC_CONFIG = "topic";
    private static final String TOPIC_DOC = "Topic to write to";

    public static final String SLEEP_CONFIG = "sleep.seconds";
    private static final String SLEEP_DOC = "Time in seconds that connector will wait until querying api again";

    private final String url;
    private final String topic;
    private final int sleepInSeconds;

    public RandomLongSourceConnectorConfig(Map<?, ?> originals) {
        super(config(), originals);
        this.url = getString(API_ENDPOINT);
        this.topic = getString(TOPIC_CONFIG);
        this.sleepInSeconds = getInt(SLEEP_CONFIG);
    }

    public static ConfigDef config() {
        return new ConfigDef()
                .define(API_ENDPOINT, ConfigDef.Type.STRING, NO_DEFAULT_VALUE, ConfigDef.Importance.HIGH, API_ENDPOINT_DOC)
                .define(TOPIC_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, TOPIC_DOC)
                .define(SLEEP_CONFIG, ConfigDef.Type.INT, 60, ConfigDef.Importance.MEDIUM, SLEEP_DOC);
    }

    public String getUrl() {
        return url;
    }

    public String getTopic() {
        return topic;
    }

    public int getSleepInSeconds() {
        return sleepInSeconds;
    }
}

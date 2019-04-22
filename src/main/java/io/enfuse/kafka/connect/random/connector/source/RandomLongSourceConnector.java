package io.enfuse.kafka.connect.random.connector.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

public class RandomLongSourceConnector extends SourceConnector {
    public static final String VERSION = "0.1.0";

    @Override
    public void start(Map<String, String> props) {
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
        return new ArrayList<>();
    }

    @Override
    public String version() {
        return VERSION;
    }

}

package io.enfuse.kafka.connect.random.connector.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

public class RandomLongSourceTask extends SourceTask {
    public static final String VERSION = "0.1.0";

    public void start(Map<String, String> props) {
    }

    @Override
    public synchronized void stop() {
    }

    @Override
    public List<SourceRecord> poll() {
        return new ArrayList<>();
    }

    @Override
    public String version() {
        return VERSION;
    }
}

package io.enfuse.kafka.connect.connector;

import io.enfuse.kafka.connect.connector.config.RandomLongSourceConnectorConfig;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.enfuse.kafka.connect.connector.util.RandomLongHttpClient.httpClient;
import static io.enfuse.kafka.connect.connector.util.RandomLongSchemas.VALUE_SCHEMA;

public class RandomLongSourceTask extends SourceTask {
    private static Logger logger = LoggerFactory.getLogger(RandomLongSourceTask.class);

    private RandomLongSourceConnectorConfig config;
    private Function<Map<String, String>, RandomLongSourceConnectorConfig> configFactory =
            RandomLongSourceConnectorConfig::new;
    private CountDownLatch stopLatch = new CountDownLatch(1);
    private boolean shouldWait = false;

    public void start(Map<String, String> props) {
        config = configFactory.apply(props);
        logger.info("Starting task with properties {}", props);
    }

    @Override
    public synchronized void stop() {
        stopLatch.countDown();
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        boolean shouldStop = false;
        if (shouldWait) {
            logger.debug("Waiting for {} seconds for the next poll", config.getSleepInSeconds());
            shouldStop = stopLatch.await(config.getSleepInSeconds(), TimeUnit.SECONDS);
        }
        if (!shouldStop) {
            logger.debug("Started new polling");
            shouldWait = true;
            return getSourceRecords();
        } else {
            logger.debug("Received signal to stop, didn't poll anything");
            return null;
        }
    }

    private List<SourceRecord> getSourceRecords() {
        SourceRecord record = new SourceRecord(
                null,
                null,
                config.getTopic(),
                VALUE_SCHEMA,
                getRandomLongFromApi());
        return Collections.singletonList(record);
    }

    private Long getRandomLongFromApi() {
        HttpGet httpGet = new HttpGet("http://" + config.getUrl() + "/random/long");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return Long.parseLong(EntityUtils.toString(response.getEntity()));
        } catch (ClientProtocolException e) {
            logger.error("Error consuming GET /random/long: {}", e);
        } catch (IOException e) {
            logger.error("IO Error: {}", e);
        }
        return null;
    }

    @Override
    public String version() {
        return RandomLongSourceConnector.VERSION;
    }
}

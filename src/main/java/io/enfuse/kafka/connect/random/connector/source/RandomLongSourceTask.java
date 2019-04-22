package io.enfuse.kafka.connect.random.connector.source;

import io.enfuse.kafka.connect.random.connector.source.config.RandomLongSourceConnectorConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.connect.data.Timestamp;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static io.enfuse.kafka.connect.random.connector.source.RandomLongSchemas.VALUE_SCHEMA;

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
        String randomValue = getRandomFromApi();

        Map<String, String> sourcePartition = Collections.singletonMap("url", config.getUrl());
        Map<String, Timestamp> sourceOffset = Collections.singletonMap("date", new Timestamp());

        SourceRecord record = new SourceRecord(sourcePartition, sourceOffset, config.getTopic(), VALUE_SCHEMA, randomValue);
        return Collections.singletonList(record);
    }

    private String getRandomFromApi() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(config.getUrl() + "/random/long");
        HttpEntity entity;

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            entity = response.getEntity();
            EntityUtils.consume(entity);
            return EntityUtils.toString(entity);
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

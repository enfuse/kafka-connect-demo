package io.enfuse.kafka.connect.connector.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RandomLongHttpClient {

    public static CloseableHttpClient httpClient = HttpClients.createDefault();
}

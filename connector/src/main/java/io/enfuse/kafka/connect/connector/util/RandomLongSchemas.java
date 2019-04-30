package io.enfuse.kafka.connect.connector.util;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class RandomLongSchemas {

    public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name("random_long")
            .version(1)
            .field("value", Schema.INT64_SCHEMA)
            .build();
}

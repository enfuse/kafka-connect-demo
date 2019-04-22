package io.enfuse.kafka.connect.random.connector.source;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class RandomLongSchemas {

    public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name("value")
            .version(1)
            .field("value", Schema.STRING_SCHEMA)
            .build();
}

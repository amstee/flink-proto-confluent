# flink-proto-confluent

A confluent protobuf format for flink.

## WIP

This is a work in progress. Should be usable already though, just run:
```bash
./gradlew build shadowJar

# Then copy the jar to your flink lib folder

COPY flink-proto-confluent/build/libs/proto-confluent.jar /opt/flink/lib/proto-confluent.jar
```

## Run this locally

```yaml
docker-compose up

# Go to rp-console and create topic: http://localhost:8080
# topic name: example

# >>>>>>>>>>>>>> Send some proto events to example topic (+ make sure schema is registered)

# Connect to flink sql
docker exec -it FLINK_JOB_MANAGER bin/sql-client.sh

# Run the following SQL
```

```sql
SET sql-client.verbose = true;
SET 'sql-client.execution.result-mode' = 'tableau';

    
CREATE TABLE example (

  `kafka_key_id` STRING,
  `nested_value` ROW (
    `value` STRING
  )

) WITH (

  'connector' = 'kafka',
  'topic' = 'example',
  'properties.bootstrap.servers' = 'kafka:29092',
  'properties.group.id' = 'testGroup',
  'scan.startup.mode' = 'earliest-offset',

  'key.format' = 'proto-confluent',
  'key.proto-confluent.url' = 'http://karapace-registry:8085',
  'key.proto-confluent.topic' = 'example',
  'key.proto-confluent.is_key' = 'true',
  'key.fields' = 'kafka_key_id',
  'key.fields-prefix' = 'kafka_key_',

  'value.format' = 'proto-confluent',
  'value.proto-confluent.url' = 'http://karapace-registry:8085',
  'value.proto-confluent.topic' = 'example',
  'value.proto-confluent.is_key' = 'false',
  'value.fields-include' = 'EXCEPT_KEY'
);
```

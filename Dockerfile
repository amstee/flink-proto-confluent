FROM flink:1.18-java11

RUN wget -P /opt/flink/lib https://repo.maven.apache.org/maven2/org/apache/flink/flink-sql-connector-kafka/3.1.0-1.18/flink-sql-connector-kafka-3.1.0-1.18.jar

COPY flink-proto-confluent-registry/build/libs/proto-confluent.jar /opt/flink/lib/proto-confluent.jar

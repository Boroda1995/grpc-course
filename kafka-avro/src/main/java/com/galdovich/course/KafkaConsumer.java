package com.galdovich.course;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class KafkaConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avro-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            KafkaAvroDeserializer.class.getName());
        props.put("schema.registry.url", "http://localhost:8081");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        org.apache.kafka.clients.consumer.KafkaConsumer<String, Order> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("avro-messages"));

        try {
            while (true) {
                ConsumerRecords<String, Order> records =
                    consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, Order> record : records) {
                    Order message = record.value();
                    log.info("Received message: {}, {}",
                        message.getId(), message.getContent());
                    log.info("Schema ID: {}",
                        record.headers().lastHeader("schemaId"));
                }
            }
        } finally {
            consumer.close();
        }
    }
}
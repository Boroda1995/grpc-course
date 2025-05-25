package com.galdovich.course;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Slf4j
public class KafkaProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            KafkaAvroSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", "http://localhost:8081");

        Producer<String, Order> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);

        Order message = Order.newBuilder()
            .setId("Test-id")
            .setContent("Custom Content")
            .build();

        ProducerRecord<String, Order> record =
            new ProducerRecord<>("avro-messages", message);

        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                log.info("Sent message. Offset: {}, Partition: {}",
                    metadata.offset(), metadata.partition());
            } else {
                exception.printStackTrace();
            }
        });

        producer.flush();
        producer.close();
    }
}
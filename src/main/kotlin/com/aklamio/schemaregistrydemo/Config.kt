package com.aklamio.schemaregistrydemo

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*

@Configuration
@EnableKafka
class Config {

    private val kafkaURL = "localhost:29092"

    private val schemaRegistryURL = "http://localhost:8085"


    /**
     * ----------------------------------------------------------------------
     * General configuration
     * ----------------------------------------------------------------------
     */
    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaURL
        return KafkaAdmin(configs)
    }

    @Bean
    fun topic(): NewTopic {
        return NewTopic("avro-topic", 1, 1.toShort())
    }


    /**
     * ----------------------------------------------------------------------
     * Producer configuration
     * ----------------------------------------------------------------------
     */
    @Bean
    fun producerFactory(): ProducerFactory<String, EndUser> {
        val props: MutableMap<String, Any> = hashMapOf()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaURL
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryURL
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, EndUser> {
        return KafkaTemplate(producerFactory())
    }


    /**
     * ----------------------------------------------------------------------
     * Consumer configuration
     * ----------------------------------------------------------------------
     */
    @Bean
    fun consumerFactory(): ConsumerFactory<String, EndUser> {
        val props: MutableMap<String, Any> = hashMapOf()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaURL
        props[ConsumerConfig.GROUP_ID_CONFIG] = "testConsumerGroup"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
        props["schema.registry.url"] = schemaRegistryURL
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, EndUser> {
        return ConcurrentKafkaListenerContainerFactory<String, EndUser>().apply {
            consumerFactory = consumerFactory()
        }
    }


}
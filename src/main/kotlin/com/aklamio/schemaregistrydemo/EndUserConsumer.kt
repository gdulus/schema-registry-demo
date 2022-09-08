package com.aklamio.schemaregistrydemo

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class EndUserConsumer {

    @KafkaListener(topics = ["avro-topic"])
    fun listenGroupFoo(message: ConsumerRecord<String, EndUser>) {
        println("==== >>> Received Message : ${message.value()}")
    }

}
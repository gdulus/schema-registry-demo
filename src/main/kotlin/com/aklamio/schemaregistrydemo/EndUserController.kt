package com.aklamio.schemaregistrydemo

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID.randomUUID
import java.util.concurrent.ThreadLocalRandom.current
import kotlin.math.abs

@RestController
class EndUserController(val kafkaTemplate: KafkaTemplate<String, EndUser>) {

    @GetMapping("/")
    fun execute(): String {
        kafkaTemplate.send(
            "avro-topic",
            EndUser.newBuilder().apply {
                id = abs(current().nextInt())
                name = randomUUID().toString()
            }.build()
        )
        return "SENT"
    }

}
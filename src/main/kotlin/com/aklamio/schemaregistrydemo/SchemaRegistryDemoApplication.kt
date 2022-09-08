package com.aklamio.schemaregistrydemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan
class SchemaRegistryDemoApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<SchemaRegistryDemoApplication>(*args)
        }
    }

}


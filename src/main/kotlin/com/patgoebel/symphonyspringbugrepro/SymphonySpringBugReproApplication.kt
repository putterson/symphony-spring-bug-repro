package com.patgoebel.symphonyspringbugrepro

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.beans.factory.config.DependencyDescriptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@SpringBootApplication
class SymphonySpringBugReproApplication

fun main(args: Array<String>) {
	runApplication<SymphonySpringBugReproApplication>(*args)
}

@Configuration
class LoggingConfiguration {
    @Bean
    @Scope("prototype")
    fun produceLogger(injectionPoint: InjectionPoint?): Logger {
        val classOnWired = injectionPoint?.member?.declaringClass ?: injectionPoint?.field?.declaringClass
        return when(classOnWired) {
            null -> LoggerFactory.getLogger("UnknownInjectionPointLogger")
            else -> LoggerFactory.getLogger(classOnWired)
        }
    }

    @Bean
    @Scope("request")
    fun produceReqResource(): Any {
        return object {}
    }
}


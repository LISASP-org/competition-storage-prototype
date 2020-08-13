package org.lisasp.attachmentstorage.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class MessageQueueConfig {

    @Bean
    ObjectMapper createJSONMapper() {
        return new ObjectMapper().registerModule(new KotlinModule());
    }
}

package com.gft.calculator.config;

import io.corp.calculator.TracerAPI;
import io.corp.calculator.TracerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracerConfiguration {

    @Bean
    public TracerAPI tracer() {
        TracerImpl tracer = new TracerImpl();

        return new TracerAPI() {
            @Override
            public <T> void trace(T result) {
                tracer.trace(result);
            }
        };
    }
}

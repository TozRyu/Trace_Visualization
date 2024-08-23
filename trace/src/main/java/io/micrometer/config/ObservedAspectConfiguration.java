package io.micrometer.config;

import io.micrometer.handler.SpanHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservedAspectConfiguration {

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        observationRegistry.observationConfig().observationHandler(new SpanHandler());
        return new ObservedAspect(observationRegistry);
    }

}

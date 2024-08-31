package io.micrometer.test;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

public class ExampleObservation {

    private final ObservationRegistry registry;

    ExampleObservation(ObservationRegistry registry) {
        this.registry = registry;
    }

    void run() {
        Observation.createNotStarted("foo", registry)
                .lowCardinalityKeyValue("lowTag", "lowTagValue")
                .highCardinalityKeyValue("highTag", "highTagValue")
                .observe(() -> System.out.println("Hello"));
    }

}

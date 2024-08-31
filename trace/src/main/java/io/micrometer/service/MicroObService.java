package io.micrometer.service;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

@Service
public class MicroObService {

    /**
     * Observed 需要调用后出现在 /actuator/metrics 中
     */
    @Observed(name = "test.call", contextualName = "test#call",
            lowCardinalityKeyValues = {"abc", "123", "test", "42"})
    public void call() {
        System.out.println("call");
    }
}

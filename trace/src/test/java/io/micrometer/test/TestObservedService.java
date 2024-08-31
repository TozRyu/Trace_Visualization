package io.micrometer.test;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import io.micrometer.observation.annotation.Observed;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.observation.tck.ObservationRegistryAssert;
import io.micrometer.observation.tck.TestObservationRegistry;
import io.micrometer.observation.tck.TestObservationRegistryAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.condition.AllOf;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class TestObservedService {

    static class TestObService {

        @Observed(name = "test.call", contextualName = "test#call",
                lowCardinalityKeyValues = {"abc", "123", "test", "42"})
        void call() {
            System.out.println("call");
        }

    }

    @Test
    void should_assert_your_observation() {
        // create a test registry in your tests
        TestObservationRegistry registry = TestObservationRegistry.create();

        // run your production code with the TestObservationRegistry
        new ExampleObservation(registry).run();

        // check your observation
        TestObservationRegistryAssert.assertThat(registry)
                .doesNotHaveAnyRemainingCurrentObservation()
                .hasObservationWithNameEqualTo("foo")
                .that()
                .hasHighCardinalityKeyValue("highTag", "highTagValue")
                .hasLowCardinalityKeyValue("lowTag", "lowTagValue")
                .hasBeenStarted()
                .hasBeenStopped();
    }

    @Test
    void should_assert_our_observation() {
// create a test registry
        TestObservationRegistry registry = TestObservationRegistry.create();
// add a system out printing handler
        registry.observationConfig().observationHandler(new ObservationTextPublisher());

// create a proxy around the observed service
        AspectJProxyFactory pf = new AspectJProxyFactory(new TestObService());
        pf.addAspect(new ObservedAspect(registry));

// make a call
        TestObService service = pf.getProxy();
        service.call();

// assert that observation has been properly created
        TestObservationRegistryAssert.assertThat(registry)
                .hasSingleObservationThat()
                .hasBeenStopped()
                .hasNameEqualTo("test.call")
                .hasContextualNameEqualTo("test#call")
                .hasLowCardinalityKeyValue("abc", "123")
                .hasLowCardinalityKeyValue("test", "42")
                .hasLowCardinalityKeyValue("class", TestObService.class.getName())
                .hasLowCardinalityKeyValue("method", "call").doesNotHaveError();
    }


    public static void main(String[] args) {
        // create a test registry
        ObservationRegistry registry = ObservationRegistry.create();
        // add a system out printing handler
        registry.observationConfig().observationHandler(new ObservationTextPublisher());


        // create a proxy around the observed service
        AspectJProxyFactory pf = new AspectJProxyFactory(new TestObService());
        pf.addAspect(new ObservedAspect(registry));

        // make a call
        TestObService service = pf.getProxy();
        service.call();
        // 注册服务方法上的观察
        Observation observation = registry.getCurrentObservation();

        Observation.createNotStarted("foo", registry)
                .lowCardinalityKeyValue("lowTag", "lowTagValue")
                .highCardinalityKeyValue("highTag", "highTagValue")
                .observe(() -> System.out.println("Hello"));


        Condition<Observed> conditions = AllOf.allOf(
                new Condition<>((Observed observed) -> {
                    return observed.name().equals("test.call");
                }, "name is test.call"),
                new Condition<>((Observed observed) -> {
                    return observed.contextualName().equals("test#call");
                }, "contextualName is adult")
        );

        ObservationRegistryAssert.assertThat(registry).isNotNull();
//        ObservationRegistryAssert.assertThat(registry).is(conditions);
//        Condition<Observed> condition = org.assertj.core.api.Assertions.assertThat(Observed.class)
//                .hasPublicFields()
//                .hasName("myObserved")
//                .hasTags("env", "dev")
//                .hasTags("region", "north")
//                .hasMetric("myMetric", 10.0);

//        ObservationRegistryAssert.assertThat(registry).is(conditions);

        // assert that observation has been properly created
//        TestObservationRegistryAssert.assertThat(registry)
//                .hasSingleObservationThat()
//                .hasBeenStopped()
//                .hasNameEqualTo("test.call")
//                .hasContextualNameEqualTo("test#call")
//                .hasLowCardinalityKeyValue("abc", "123")
//                .hasLowCardinalityKeyValue("test", "42")
//                .hasLowCardinalityKeyValue("class", TestObService.class.getName())
//                .hasLowCardinalityKeyValue("method", "call").doesNotHaveError();

    }

}

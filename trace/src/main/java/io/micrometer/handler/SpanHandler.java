package io.micrometer.handler;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.log4j.Log4j2;

/**
 * 观察span的创建和销毁
 */
@Log4j2
public class SpanHandler implements ObservationHandler<Observation.Context> {

	@Override
	public void onStart(Observation.Context context) {
		log.info("SpanHandler Starting context {} ", context);
	}

	@Override
	public void onStop(Observation.Context context) {
		log.info("SpanHandler Stopping context {} ", context);
	}

	@Override
	public boolean supportsContext(Observation.Context context) {
		return true;
	}

}

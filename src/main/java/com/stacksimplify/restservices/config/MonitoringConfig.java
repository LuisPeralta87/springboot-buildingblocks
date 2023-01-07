package com.stacksimplify.restservices.config;

import org.springframework.context.annotation.Configuration;

import io.micrometer.appoptics.AppOpticsConfig;
import io.micrometer.appoptics.AppOpticsMeterRegistry;
import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class MonitoringConfig {

	AppOpticsConfig appopticsConfig = new AppOpticsConfig() {
	    @Override
	    public String apiToken() {
	        return "2AyJoy0TeKUwohRgO1nEYQIMjLgspPBi2CC0Z9FtAAC5v-wNMEIbWbF5a-JnJSDNyS-jEoU";
	    }

	    @Override
	    @Nullable
	    public String get(String k) {
	        return null;
	    }
	};
	MeterRegistry registry = new AppOpticsMeterRegistry(appopticsConfig, Clock.SYSTEM);
}

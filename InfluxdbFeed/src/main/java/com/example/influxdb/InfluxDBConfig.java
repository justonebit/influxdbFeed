/**
 * 
 */
package com.example.influxdb;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

/**
 * @author Avis
 *
 */
@Configuration
public class InfluxDBConfig {

	public static final String JVM_GC = "jvm_gc";
	public static final String JVM_MEMORY = "jvm_memory";
	public static final String JVM_MEMORY_POOLS = "jvm_memory_pools";
	public static final String JVM_THREAD_STATE = "jvm_thread-states";
	
	@Autowired
	private InfluxDBTemplate influxDBTemplate;

	@Bean
	public InfluxDBReporter getReporter() {
		InfluxDBReporter reporter = new InfluxDBReporter(getMetricRegistry(),
															MetricFilter.ALL, 
													   TimeUnit.MILLISECONDS, 
													   TimeUnit.MILLISECONDS,
													   		influxDBTemplate);
		reporter.start(1, TimeUnit.SECONDS);
		return reporter;
	}

	@Bean
	public MetricRegistry getMetricRegistry() {
		MetricRegistry registry = new MetricRegistry();
		registry.register(JVM_GC, new GarbageCollectorMetricSet());
		registry.register(JVM_MEMORY, new MemoryUsageGaugeSet());
		registry.register(JVM_THREAD_STATE, new ThreadStatesGaugeSet());
		return registry;
	}
}

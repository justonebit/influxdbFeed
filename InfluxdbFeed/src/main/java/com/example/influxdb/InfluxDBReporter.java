/**
 * 
 */
package com.example.influxdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;

/**
 * @author Avis
 *
 */
public class InfluxDBReporter extends ScheduledReporter {

	private InfluxDBTemplate influxDBTemplate;

	protected InfluxDBReporter(MetricRegistry registry, MetricFilter filter,
			TimeUnit rateUnit, TimeUnit durationUnit, InfluxDBTemplate influxDBTemplate) {
		super(registry, "influxDBReporter", filter, rateUnit, durationUnit);
		this.influxDBTemplate = influxDBTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.codahale.metrics.ScheduledReporter#report(java.util.SortedMap,
	 * java.util.SortedMap, java.util.SortedMap, java.util.SortedMap,
	 * java.util.SortedMap)
	 */
	@Override
	public void report(SortedMap<String, Gauge> arg0, SortedMap<String, Counter> arg1, 
				   SortedMap<String, Histogram> arg2,   SortedMap<String, Meter> arg3, SortedMap<String, Timer> arg4) {
		List<Point> pntLst = new ArrayList<Point>();
		if (!arg0.isEmpty()) {
			Builder jvmGCPntBldr = Point.measurement(InfluxDBConfig.JVM_GC).addField("app", influxDBTemplate.getAppServer());
			Builder jvmMemPntBldr = Point.measurement(InfluxDBConfig.JVM_MEMORY).addField("app", influxDBTemplate.getAppServer());
			Builder jvmMemPoolsPntBldr = Point.measurement(InfluxDBConfig.JVM_MEMORY_POOLS).addField("app", influxDBTemplate.getAppServer());
			Builder jvmThreadPntBldr = Point.measurement(InfluxDBConfig.JVM_THREAD_STATE).addField("app", influxDBTemplate.getAppServer());
			for (Map.Entry<String, Gauge> entry : arg0.entrySet()) {
				if (entry.getKey().indexOf(InfluxDBConfig.JVM_GC) >= 0) {
					jvmGCPntBldr.addField(entry.getKey().substring(InfluxDBConfig.JVM_GC.length() + 1),
							((Long) entry.getValue().getValue()).longValue());
				} else if (entry.getKey().indexOf(InfluxDBConfig.JVM_MEMORY) >= 0) {
					if (entry.getKey().indexOf("total") >= 0 || entry.getKey().indexOf("usage") >= 0) {
						continue;
					}
					if (entry.getKey().indexOf("pools") >= 0) {
						jvmMemPoolsPntBldr.addField(entry.getKey().substring(InfluxDBConfig.JVM_MEMORY_POOLS.length() + 1),
								((Long) entry.getValue().getValue()).longValue() / (1024 * 1024));
					} else {
						jvmMemPntBldr.addField(entry.getKey().substring(InfluxDBConfig.JVM_MEMORY.length() + 1),
								((Long) entry.getValue().getValue()).longValue() / (1024 * 1024));
					}
				} else if (entry.getKey().indexOf(InfluxDBConfig.JVM_THREAD_STATE) >= 0 && entry.getKey().indexOf("count") >= 0) {
					jvmThreadPntBldr.addField(entry.getKey().substring(InfluxDBConfig.JVM_THREAD_STATE.length() + 1),
							((Integer) entry.getValue().getValue()).intValue());
				}
			}
			pntLst.add(jvmGCPntBldr.build());
			pntLst.add(jvmMemPntBldr.build());
			pntLst.add(jvmMemPoolsPntBldr.build());
			pntLst.add(jvmThreadPntBldr.build());
			influxDBTemplate.write(pntLst, null, ConsistencyLevel.ALL);
		}
	}
}

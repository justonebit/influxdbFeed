/**
 * 
 */
package com.example.influxdb;

import java.util.List;

import org.influxdb.dto.Point;

/**
 * @author Avis
 *
 */
public class InfluxDBLogger implements Runnable {

	private InfluxDBTemplate influxDBTemplate;
	private List<Point> pntList;
	
	public InfluxDBLogger(InfluxDBTemplate influxDBTemplate, List<Point> pntList) {
		super();
		this.influxDBTemplate = influxDBTemplate;
		this.pntList = pntList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		influxDBTemplate.write(pntList, null, ConsistencyLevel.ALL);
	}

}

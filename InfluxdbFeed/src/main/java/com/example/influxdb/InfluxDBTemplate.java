/**
 * 
 */
package com.example.influxdb;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Avis
 *
 */
@Configuration
public class InfluxDBTemplate implements InitializingBean {

	private String appServer;
	
	@Autowired
	private InfluxDBConnectionFactory influxDBConnectionFactory;

	public void write(List<Point> pntLst, String retentionPolicy, ConsistencyLevel consistencyLevel) {
		final String database = influxDBConnectionFactory.getInfluxDBSettings().getDatabase();
		final String rPolicy = retentionPolicy != null ? retentionPolicy : influxDBConnectionFactory.getInfluxDBSettings().getRetentionPolicy();
		final BatchPoints batchPts = BatchPoints.database(database).retentionPolicy(rPolicy).consistency(consistencyLevel).build();
		pntLst.stream().forEach((pnt)-> batchPts.point(pnt));
		influxDBConnectionFactory.getInfluxdb().write(batchPts);
	}
	
	public QueryResult query(Query query){
		return influxDBConnectionFactory.getInfluxdb().query(query);	
	}
	
	public QueryResult query(Query query, TimeUnit timeUnit){
		return influxDBConnectionFactory.getInfluxdb().query(query, timeUnit);	
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.appServer = influxDBConnectionFactory.getInfluxDBSettings().getAppServer();
	}

	public String getAppServer() {
		return appServer;
	}
}
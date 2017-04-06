/**
 * 
 */
package com.example.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Avis
 *
 */
@Configuration
public class InfluxDBConnectionFactory implements InitializingBean {

	private InfluxDB influxdb;
	
	@Autowired
	private InfluxDBSettings influxDBSettings;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if(influxdb == null){
			if(this.getInfluxDBSettings() != null) {
				influxdb = InfluxDBFactory.connect(influxDBSettings.getUrl(), influxDBSettings.getUsername(), influxDBSettings.getPassword());
			}else{
				throw new Exception("InfluxDBSettings cant be NULL");
			}
		}
	}

	public InfluxDB getInfluxdb() {
		return influxdb;
	}

	public InfluxDBSettings getInfluxDBSettings() {
		return influxDBSettings;
	}

}
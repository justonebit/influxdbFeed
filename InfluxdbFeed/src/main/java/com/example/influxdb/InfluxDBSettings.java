/**
 * 
 */
package com.example.influxdb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Avis
 *
 */
@ConfigurationProperties(prefix = "influxdb")
@Configuration
public class InfluxDBSettings {

	private String url;
	private String host;
	private String port;
	private String username;
	private String password;
	private String database;	
	private String appServer;
	private String retentionPolicy = "default";
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}
	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}
	/**
	 * @return the appServer
	 */
	public String getAppServer() {
		return appServer;
	}
	/**
	 * @param appServer the appServer to set
	 */
	public void setAppServer(String appServer) {
		this.appServer = appServer;
	}
	/**
	 * @return the retentionPolicy
	 */
	public String getRetentionPolicy() {
		return retentionPolicy;
	}
	/**
	 * @param retentionPolicy the retentionPolicy to set
	 */
	public void setRetentionPolicy(String retentionPolicy) {
		this.retentionPolicy = retentionPolicy;
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InfluxDBSettings [url=" + url + ", host=" + host + ", port="
				+ port + ", username=" + username + ", password=" + password
				+ ", database=" + database + ", appServer=" + appServer
				+ ", retentionPolicy=" + retentionPolicy + "]";
	}	
}
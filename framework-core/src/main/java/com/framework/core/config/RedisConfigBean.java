package com.framework.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigBean {

	private int database;

	private String host;

	private int port;

	private String password;

	private int timeout = 60000;

	private Pool pool = new Pool();

	public static class Pool {

		private int maxActive = 64;
		private int maxIdle = 16;
		private int minIdle = 0;
		private long maxWait = -1;
		private boolean testWhileIdle = true;
		private boolean testOnBorrow = false;
		private boolean testOnReturn = false;
		private boolean testOnCreate = false;

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public long getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(long maxWait) {
			this.maxWait = maxWait;
		}

		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public boolean isTestOnReturn() {
			return testOnReturn;
		}

		public void setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}

		public boolean isTestOnCreate() {
			return testOnCreate;
		}

		public void setTestOnCreate(boolean testOnCreate) {
			this.testOnCreate = testOnCreate;
		}
		
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	@Override
	public String toString() {
		return "RedisConfig [host=" + host + ", password=" + password + ", port=" + port + ", timeout=" + timeout + "]";
	}

}
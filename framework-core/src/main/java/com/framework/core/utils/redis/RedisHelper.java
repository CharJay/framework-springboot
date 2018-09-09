package com.framework.core.utils.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public final class RedisHelper {

	/**
	 * 使用Redis的获取锁。获取锁后可以主动删除锁来解锁或等待锁超时自动释放。
	 * 
	 * @param jedis
	 * @param lockKey
	 *            锁
	 * @param expire
	 *            锁释放时间(ms)
	 * @return true=获得锁，false=未获得锁
	 */
	public static boolean lockSession(Jedis jedis, String lockKey, long expire) {
		boolean locked = true;
		long lockTTL = jedis.pttl(lockKey);
		if (lockTTL == -1)
			jedis.del(lockKey);

		if (lockTTL < 0) {
			Transaction lockJT = jedis.multi();
			lockJT.setnx(lockKey, "1");
			lockJT.pexpire(lockKey, expire);
			List<Object> lockResps = lockJT.exec();
			if (Long.valueOf(0).equals(lockResps.get(0))) {
				locked = false;
			}
		} else {
			locked = false;
		}
		return locked;
	}

	public static boolean unlockSessionQuietly(Jedis jedis, String lockKey) {
		if (jedis != null) {
			try {
				jedis.del(lockKey);
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static void closeQuietly(Jedis jedis) {
		if (jedis != null)
			try {
				jedis.close();
			} catch (Exception e) {
			}
	}
}

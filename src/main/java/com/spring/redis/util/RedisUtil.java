package com.spring.redis.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

//@Component
public class RedisUtil {
	//@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	public boolean set(String key, Object value) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				redisTemplate.opsForValue().set(key, value);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean expire(String key, long time) {
		try {
			if (StringUtils.isNotEmpty(key) && time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public long getExpire(String key) {
		if (StringUtils.isNotEmpty(key)) {
			return redisTemplate.getExpire(key, TimeUnit.SECONDS);
		}
		return -1L;
	}

	public boolean exists(String key) {
		try {
			if (StringUtils.isNotEmpty(key)) {
				return redisTemplate.hasKey(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void delete(String... keys) {
		if (keys != null && keys.length > 0) {
			if (keys.length == 1) {
				redisTemplate.delete(keys[0]);
			} else {
				// 일괄 삭제
				redisTemplate.delete(CollectionUtils.arrayToList(keys));
			}
		}
	}

	public boolean set(String key, Object value, long time) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				if (time > 0) {
					redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
				} else {
					set(key, value);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public long increment(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("증분 계수는 0보다 커야합니다.");
		}
		if (StringUtils.isNotEmpty(key)) {
			return redisTemplate.opsForValue().increment(key, delta);
		} else {
			return -1L;
		}
	}

	public long decrement(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("감소 계수는 0보다 커야합니다.");
		}
		if (StringUtils.isNotEmpty(key)) {
			return redisTemplate.opsForValue().increment(key, -delta);
		} else {
			return -1L;
		}
	}

	public Object getHash(String key, String item) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
			return redisTemplate.opsForHash().get(key, item);
		} else {
			return null;
		}
	}

	public Map<Object, Object> getHashMap(String key) {
		if (StringUtils.isNotEmpty(key)) {
			return redisTemplate.opsForHash().entries(key);
		} else {
			return null;
		}
	}

	public boolean setHashMap(String key, Map<String, Object> map) {
		try {
			if (StringUtils.isNotEmpty(key) && null != map && map.size() > 0) {
				redisTemplate.opsForHash().putAll(key, map);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setHashMap(String key, Map<String, Object> map, long time) {
		try {
			if (StringUtils.isNotEmpty(key) && null != map && map.size() > 0) {
				redisTemplate.opsForHash().putAll(key, map);
				if (time > 0) {
					expire(key, time);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setHash(String key, String item, Object value) {
		try {
			if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item) && null != value) {
				redisTemplate.opsForHash().put(key, item, value);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setHash(String key, String item, Object value, long time) {
		try {
			if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item) && null != value) {
				redisTemplate.opsForHash().put(key, item, value);
				if (time > 0) {
					expire(key, time);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void deleteHash(String key, Object... item) {
		if (StringUtils.isNotEmpty(key) && null != item) {
			redisTemplate.opsForHash().delete(key, item);
		}
	}

	public boolean existHashKey(String key, String item) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
			return redisTemplate.opsForHash().hasKey(key, item);
		} else {
			return false;
		}
	}

	public double incrementHash(String key, String item, double by) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
			return redisTemplate.opsForHash().increment(key, item, by);
		} else {
			return -1;
		}
	}

	public double decrementHash(String key, String item, double by) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(item)) {
			return redisTemplate.opsForHash().increment(key, item, -by);
		} else {
			return -1;
		}
	}

	// ==============================set==============================
	public Set<Object> getSet(String key) {
		try {
			if (StringUtils.isNotEmpty(key)) {
				return redisTemplate.opsForSet().members(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean existSetKey(String key, Object value) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				return redisTemplate.opsForSet().isMember(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public long setSet(String key, Object... values) {
		try {
			if (StringUtils.isNotEmpty(key) && null != values) {
				return redisTemplate.opsForSet().add(key, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long setSet(String key, long time, Object... values) {
		try {
			if (StringUtils.isNotEmpty(key) && null != values) {
				Long count = redisTemplate.opsForSet().add(key, values);
				if (time > 0)
					expire(key, time);
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long getSetSize(String key) {
		try {
			if (StringUtils.isNotEmpty(key)) {
				return redisTemplate.opsForSet().size(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long removeSet(String key, Object... values) {
		try {
			if (StringUtils.isNotEmpty(key) && null != values) {
				Long count = redisTemplate.opsForSet().remove(key, values);
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// ==============================list==============================
	public List<Object> getList(String key, long start, long end) {
		try {
			if (StringUtils.isNotEmpty(key)) {
				return redisTemplate.opsForList().range(key, start, end);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getListSize(String key) {
		try {
			if (StringUtils.isNotEmpty(key)) {
				return redisTemplate.opsForList().size(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Object getListIndex(String key, long index) {
		try {
			if (StringUtils.isNotEmpty(key)) {
				return redisTemplate.opsForList().index(key, index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean setList(String key, Object value) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				redisTemplate.opsForList().rightPush(key, value);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setList(String key, Object value, long time) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				redisTemplate.opsForList().rightPush(key, value);
				if (time > 0)
					expire(key, time);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setList(String key, List<Object> value) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				redisTemplate.opsForList().rightPushAll(key, value);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setList(String key, List<Object> value, long time) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				redisTemplate.opsForList().rightPushAll(key, value);
				if (time > 0)
					expire(key, time);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateListIndex(String key, long index, Object value) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				redisTemplate.opsForList().set(key, index, value);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public long removeList(String key, long count, Object value) {
		try {
			if (StringUtils.isNotEmpty(key) && null != value) {
				Long remove = redisTemplate.opsForList().remove(key, count, value);
				return remove;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
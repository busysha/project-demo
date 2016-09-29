package com.project.cs.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.dto.MemberCache;
import com.project.common.util.ObjectMapperSingle;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.cs.service.RedisService;


@Service("redisService")
public class RedisServiceImpl implements RedisService {
	private static Logger logger = Logger.getLogger(RedisServiceImpl.class);
	
	@Autowired
	private RedisTemplate<Serializable, Serializable> template;
	
	private static final String defaultStrEncode = "utf-8";
	
	
	private byte[] getBytesByString(String str){
		try {
			return str.getBytes(defaultStrEncode);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			return str.getBytes();
		}
	}
	
	private String getStringByBytes(byte[] bs){
		try {
			return new String(bs,defaultStrEncode);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			return new String(bs);
		}
	}

	@Override
	public void put(final String key, final String value) {
		template.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(getBytesByString(key), getBytesByString(value));
				return null;
			}
			
		});
	}

	@Override
	public void put(final String key, final String value,final long expireTime) {
		template.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(getBytesByString(key), expireTime, getBytesByString(value));
				return null;
			}
			
		});
	}

	@Override
	public String get(final String key) {
		 return template.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bs= connection.get(getBytesByString(key));
				if(null != bs){
					return getStringByBytes(bs);
				}
				return "";
			}
		});
	}
	
	@Override
	public void delete(final String key) {
		template.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(getBytesByString(key));
				return null;
			}
			
		});
	}
	


	@Override
	public void sortedSetZAdd(final String key, final String innerKey, final double num) {
		template.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.zAdd(getBytesByString(key), num, getBytesByString(innerKey));
				return null;
			}

		});
	}

	@Override
	public void sortedSetZremove(final String key, final String innerKey) {
		template.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
				redisConnection.zRem(key.getBytes(),innerKey.getBytes());
				return null;
			}
		});
	}


	@Override
	public void increaseNumByKeyAndInnerKey(final String key, final String value, final double num) {
		template.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.zIncrBy(getBytesByString(key), num, getBytesByString(value));
				return null;
			}
			
		});
	}

	@Override
	public Set<Tuple> getRankList(final String key, final long begin, final long end) {
		return template.execute(new RedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.zRevRangeWithScores(key.getBytes(), begin,end);
			}
		});
	}

    @Override
    public Set<Tuple> getRankListAsc(final String key, final long begin, final long end) {
        return template.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zRangeWithScores(key.getBytes(), begin,end) ;
            }
        });
    }

    @Override
	public Double getIncreaseValue(final String key, final String value) {
		 return template.execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.zScore(key.getBytes(), value.getBytes());
			}
		});
	}

	@Override
	public Long delElementRank(final String key, final String value) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.zRem(key.getBytes(),value.getBytes());
			}
		});
	}

	@Override
	public boolean getBit(final String key, final int offset) {
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.getBit(key.getBytes(),offset);
			}
		});
	}

	@Override
	public Boolean hashSetPut(final String key, final String innerKey, final String innerVal) {
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.hSet(key.getBytes(),innerKey.getBytes(),innerVal.getBytes());
			}
		});
	}

	@Override
	public String hashSetGet(final String key, final String innerKey) {
		return template.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				byte[] res = redisConnection.hGet(key.getBytes(),innerKey.getBytes());
				return res == null ? null : new String(res);
			}
		});
	}

	@Override
	public Long hashSetDel(final String key, final String innerKey) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.hDel(key.getBytes(),innerKey.getBytes());
			}
		});
	}

	@Override
	public Map<byte[], byte[]> hashSetGetAll(final String key) {
		return template.execute(new RedisCallback<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.hGetAll(key.getBytes());
			}
		});
	}

	@Override
	public void hashSetIncr(final String key, final String innerKey, final long innerVal) {
		template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.hIncrBy(key.getBytes(),innerKey.getBytes(),innerVal);
			}
		});
	}
	
	@Override
	public Double increaseByVar(final String key, final double i) {
		return template.execute(new RedisCallback<Double>() {
			@Override
			public Double doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.incrBy(key.getBytes(),i);
			}
		});
	}

	@Override
	public long countRank(final String key) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.zCard(key.getBytes());
			}
		});
	}

	@Override
	public long getRankIndex(final String key, final String innerKey) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.zRank(key.getBytes(), innerKey.getBytes());
			}
		});
	}

	@Override
	public Long listLeftPush(final String key, final String val) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.lPush(getBytesByString(key),getBytesByString(val));
			}
		});
	}

	@Override
	public String listRightPop(final String key) {
		return template.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				String val = new String(redisConnection.rPop(key.getBytes()));
				return  val.length() > 0 ? val : null;
			}
		});
	}

	@Override
	public Long getListLength(final String key) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.lLen(getBytesByString(key));
			}
		});
	}

	@Override
	public void putMemberCache(final String key,final MemberCache memberCache) {
		//会员信息需要所有项目使用，所以只能存字符串
		String temp = "";
		ObjectMapper mapper = ObjectMapperSingle.getInstance();
		try {
			temp = mapper.writeValueAsString(memberCache);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		final String member = temp;
		template.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(getBytesByString(key),  getBytesByString(member));
				return null;
			}

		});
	}

	@Override
	public MemberCache getMemberCache(final String key) {
		//会员信息需要所有项目使用，所以只能取字符串
		return template.execute(new RedisCallback<MemberCache>() {

			@Override
			public MemberCache doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bs= connection.get(getBytesByString(key));
				if(null != bs){
					String temp = getStringByBytes(bs);
					if(StringUtils.isNoneBlank(temp)){
						ObjectMapper mapper = ObjectMapperSingle.getInstance();
						mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
						try {
							return mapper.readValue(temp, MemberCache.class);
						} catch (JsonParseException e) {
							logger.error(e);
						} catch (JsonMappingException e) {
							logger.error(e);
						} catch (IOException e) {
							logger.error(e);
						}
					}
				}
				return null;
			}
		});
	}
}

package com.project.cs.service;

import java.util.Map;
import java.util.Set;

import com.project.common.dto.MemberCache;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

public interface RedisService {
	
	/**
	 * 添加字符串缓存
	 * @param 字符串key
	 * @param 字符串value
	 */
	void put(final String key, final String value);
	
	/**
	 * 添加失效字符串缓存
	 * @param key 字符串key
	 * @param value 字符串value
	 * @param expireTime 缓存失效时间(秒)
	 */
	void put(final String key, final String value, final long expireTime);
	
	/**
	 * 查找字符串缓存
	 * @param key 字符串key
	 * @return
	 */
	String get(final String key);
	
	/**
	 * 删除缓存
	 * @param key
	 */
	void delete(final String key);
	

	/**
	 * 通过里面的key增加数量
	 * @param key
	 * @param value
	 * @param num
	 */
	void increaseNumByKeyAndInnerKey(final String key, final String value, final double num);

	/**
	 *
	 * @param key key
	 * @param begin 分页起始 page*limit;
	 * @param end 分页end (page+1)*limit - 1;
	 */
	Set<Tuple> getRankList(String key, long begin, long end);

	/**
	 *
	 * @param key key
	 * @param begin 分页起始 page*limit;
	 * @param end 分页end (page+1)*limit - 1;
	 */
	Set<Tuple> getRankListAsc(String key, long begin, long end);

	/**
	 * 拿到递增的里面的value的总和
	 * @param key
	 * @param value
	 * @return
	 */
	Double getIncreaseValue(final String key, final String value);

	/**
	 *删除rank中的数据
	 */
	Long delElementRank(final String key, final String value);


	/**
	 * redisBit
	 */
	boolean getBit(final String key, int offset);

	/**
	 * hashset
	 */
	Boolean hashSetPut(final String key, final String innerKey, final String innerVal);

	String hashSetGet(final String key, final String innerKey);

	Long hashSetDel(final String key, final String innerKey);

	Map<byte[], byte[]> hashSetGetAll(final String key);

	void hashSetIncr(final String key, final String innerKey, final long innerVal);
	
	/**
	 * 自增
	 * @param key
	 * @param i
	 * @return
	 */
	Double increaseByVar(final String key, final double i);

	/**
	 * 获取rank 总数
	 */
	long countRank(final String key);
	
	/**
	 * 获取排名所在索引（从0开始）
	 * @param key
	 * @param innerKey
	 * @return
	 */
	long getRankIndex(final String key, final String innerKey);

	/**
	 * list lpush
	 * @param key
	 * @param val
     * @return
     */
	Long listLeftPush(final String key, final String val);

	/**
	 * list rpop
	 * @param key
	 * @return
     */
	String listRightPop(final String key);

	/**
	 * get list length
	 * @param key
	 * @return
     */
	Long getListLength(final String key);

	void sortedSetZAdd(String key,String innerKey,double num);

	void sortedSetZremove(String key,String innerKey);
	/**
	 * 添加用户缓存
	 * @param key
	 * @param memberCache
	 */
	void putMemberCache(final String key, final MemberCache memberCache);

	/**
	 * 查找用户缓存
	 * @param key
	 * @return
	 */
	MemberCache getMemberCache(final String key);
}

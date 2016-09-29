package com.project.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.project.common.common.Constants;
import org.apache.log4j.Logger;

/**
 * 
 * 签名规则示例
 * appId: mc1000 //APP的ID
 * kvString: auth=2234&query=+982sss&store=xx@xx&zindex=0
 * sign:dfd81107d952da0e2d98b3908a9708b4
 * sign: {appId}:{sign} 如： mc1000:dfd81107d952da0e2d98b3908a9708b4
 * 
 */
public class SignatureUtil {


	private static Logger logger = Logger.getLogger(SignatureUtil.class);
	
	public static String generateSignature(String kvString, String secrectKey){
		return HmacSha1.getSignature(kvString, secrectKey);
	}
	
	public static boolean checkRequestTimeout(long requestTimestamp){
		long current = System.currentTimeMillis();
		logger.info(" sign requestTimestamp : " + requestTimestamp);
		logger.info(" sign current: " + current);
		if(current > (requestTimestamp + Long.parseLong(Constants.Sign.SIGN_TIMEOUT_MS))){
			return false;
		}else{
			return true;
		}
	}
	
	public static String buildKVString(Map<String,String> params) {
		StringBuilder kvStr = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			Set<String> keySet=params.keySet();
			List<String> keys= new ArrayList<String>();
			for(Iterator<String> it=keySet.iterator(); it.hasNext();){
				keys.add(it.next());
			}
			Collections.sort(keys);
			for(int i=0;i<keys.size();i++){
				String key = keys.get(i);
				if(i==(keys.size() -1)){
					kvStr.append(key).append("=").append(params.get(key));
				}else{
					kvStr.append(key).append("=").append(params.get(key)).append("&");
				}
			}
		}
		logger.info(" sign kvStr : " + kvStr.toString());
		String kvStringE = "";
		try {
			kvStringE = URLEncoder.encode(kvStr.toString(), "UTF-8").replaceAll("\\*", "%2A").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			logger.error(" sign encode error ", e);
		}
		logger.info( " sign kvStr encode : " + kvStringE);
		return kvStringE;
	}

	
}

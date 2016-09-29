package com.project.api.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.project.common.util.XlmEncryption;

public class HmacSha1 {
	private static final String HMAC_SHA1 = "HmacSHA1";
	
	private static Logger logger = Logger.getLogger(HmacSha1.class);
	
	public static String getSignature(String data, String key){
		try {
			return getSignature(data.getBytes("UTF-8"),key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String getSignature(byte[] data, byte[] key){  
        try {
			SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);  
			Mac mac = Mac.getInstance(HMAC_SHA1);  
			mac.init(signingKey);  
			byte[] rawHmac = mac.doFinal(data);  
			byte[] baseHmac = Base64.encodeBase64(rawHmac);
			return XlmEncryption.MD5(baseHmac);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
        return null;
    }  
}

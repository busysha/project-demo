package com.project.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class XlmEncryption {

	public static String MD5(byte[] data){
		String sReturnCode = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte b[] = md.digest();
			int i;
			StringBuffer sb = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(i));
			}

			sReturnCode = sb.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return sReturnCode;
		
		
	}
	
	/**
	 * MD5加密
	 * 
	 * @param sStr
	 * @return String
	 */
	public final static String MD5(String sStr) {
		try {
			return MD5(sStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args){
		System.out.println(XlmEncryption.MD5(XlmEncryption.MD5("123456")));
	}

}

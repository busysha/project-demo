package com.project.common.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil {
	private static ResourceBundle rb = ResourceBundle.getBundle("msg", Locale.getDefault());
	
	public static String buildMessage(String code){
		return buildMessage(code, new String[]{});
	}
	
	public static String buildMessage(String code, String ... args){
		String msgTemplate= getMessageFromProperties(code);
		if(args!=null && args.length>0){
			for(int i=0;i<args.length;i++){
				if(args[i]!=null){
					msgTemplate = msgTemplate.replaceAll("\\{" + i + "\\}", args[i]);
				}
			}
		}
		
		return msgTemplate;
	}
	private static String getMessageFromProperties(String messageCode){
		return rb.getString(messageCode);
	}

}

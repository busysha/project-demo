package com.project.common.util;


import com.qiniu.util.Auth;
import org.apache.log4j.Logger;


public class QiNiuUtil {

	public static final Logger logger = Logger.getLogger(QiNiuUtil.class);

	private static final String QINIU_ACCESS_KEY = "xxx";

	private static final String QINIU_SECRET_KEY = "xxx";

	private static final String BUCKET = "xxxx";

	private static final String url = "http://od7wbrgrq.bkt.clouddn.com/";

	public static String getToken(){
		Auth auth = Auth.create(QINIU_ACCESS_KEY,QINIU_SECRET_KEY);
		return auth.uploadToken(BUCKET);
	}

	public static String getUrl() {
		return url;
	}
}

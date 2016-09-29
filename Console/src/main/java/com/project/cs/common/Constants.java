package com.project.cs.common;

public interface Constants {
	
	
	interface System{
		
		public static final String LOG_PREFIX = "[project_console]=====>";
		public static final int DEFAULT_PAGE_SIZE = 20;
	}
	
	interface Status {
		String STATUS_ACTIVE = "A";

		String STATUS_INACTIVE = "I";

		String STATUS_PENDING = "P";
		
        String STATUS_YES = "Y";

        String STATUS_NO  = "N";

        String BLANK = "";
        
	}
	
	interface Session {
		
		public static final String KEY_SESSION_INFO = "_session_info";
		
	}
	
	interface Request {
		public static final String KEY_INFO_MESSAGE = "_info_message";
		
	}
	
	interface Message {
		public static final String SYSTEM_SUCCESS = "msg.success";
		public static final String SYSTEM_DOUBLE_SUBMIT = "msg.system.double.submit";
		public static final String SYSTEM_NO_PRIVILEGE = "msg.system.no.privilege";
		public static final String LOGIN_NAME_PASSWORD_INCORRECT = "msg.login.name.password.incorrect";
		public static final String USER_NOT_EXISTS = "msg.user.not.exists";
		public static final String USER_INACTIVE = "msg.user.inactive";
		
	}

}

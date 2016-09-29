package com.project.cs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.project.cs.common.ApplicationContextProvider;
import com.project.cs.service.RedisService;
import com.project.cs.util.CommonUtil;

public class TokenTag extends TagSupport {

	private static final long serialVersionUID = 4096860558125467873L;
	private static Logger logger = Logger.getLogger(TokenTag.class);
	private static int expTime = 300; //Default 5m
	private RedisService redisService;

	public int doEndTag() throws JspException {
		String token=CommonUtil.generateUUID();
		redisService = (RedisService) ApplicationContextProvider.getBean("redisService");
		redisService.put(token, "1", expTime);
		
        JspWriter out = this.pageContext.getOut();
        try {
            out.print("<input type='hidden' name='token' value='"+token+"'/>");
            out.flush();
        } catch (IOException e) {
        	logger.error("doEndTag",e);
        }
        return super.doEndTag();
	}
	
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}
	
	

}

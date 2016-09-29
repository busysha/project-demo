package com.project.cs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.project.cs.dto.ResultDto;
import com.project.cs.util.PageUtil;

public class PaginationTag extends TagSupport {
	private static Logger logger = Logger.getLogger(PaginationTag.class);

	private String resultKey;
	
	private String formId;

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
	

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 4096860558125467873L;

	private Object getAttribute(String key){
		if(!StringUtils.isEmpty(key)){
			Object obj=this.pageContext.getRequest().getAttribute(key);
			if(null!=obj){
				return obj;
			}
		}
		return null;
	}
	
	@Override
	public int doEndTag() throws JspException {
		/*(ServletRequest)this.pageContext.getRequest()*/
		
		ResultDto results=(ResultDto)getAttribute(resultKey);
        JspWriter out = this.pageContext.getOut();
        try {
        	String body=PageUtil.getPageBody(results,formId);
            out.print(body);
            out.flush();
        } catch (IOException e) {
        	logger.error("doEndTag",e);
        }
        return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_BODY_INCLUDE;
	}
}

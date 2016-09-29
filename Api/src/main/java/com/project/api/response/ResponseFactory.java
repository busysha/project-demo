package com.project.api.response;

import java.util.List;

import com.project.api.common.enums.BusinessTypeEnum;
import com.project.common.dto.BaseDto;

public class ResponseFactory {
	
	/**
	 * 基本返回类型
	 * @param code
	 * @param message
	 * @return
	 */
	public static Response createResponse(int code, String message){
		Response res = new Response(code, message);
		return res;
	}
	
	public static Response createDefaultSuccessResponse(){
		return createResponse(BusinessTypeEnum.SUCCESS.getCode(), BusinessTypeEnum.SUCCESS.getDescription());
	}
	
	public static Response createSuccessResponseWithMessage(String message){
		return createResponse(BusinessTypeEnum.SUCCESS.getCode(), message);
	}
	
	
	public static ResponseWithObjectData createResponse(int code, String message, Object data){
		ResponseWithObjectData res = new ResponseWithObjectData(code, message, data);
		return res;
	}
	
	public static ResponseWithArrayData createResponse(int code, String message, List<? extends BaseDto> data){
		ResponseWithArrayData res = new ResponseWithArrayData(code, message, data);
		return res;
	}
	
	public static ResponseWithPagingArrayData createResponse(int code, String message, int currentPage, int pageSize, int totalNum, List<? extends BaseDto> data){
		ResponseWithPagingArrayData res = new ResponseWithPagingArrayData(code, message, currentPage, pageSize, totalNum, data);
		return res;
	}

	public static ResponseWithPagingArrayData createResponse(int code, String message, int currentPage, int pageSize, int totalNum, List<? extends BaseDto> data,Object extra){
		ResponseWithPagingArrayData res = new ResponseWithPagingArrayData(code, message, currentPage, pageSize, totalNum, data, extra);
		return res;
	}
}

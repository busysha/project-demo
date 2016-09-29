package com.project.cs.util;

import org.apache.log4j.Logger;

import com.project.cs.dto.ResultDto;

/**
 * 页面公共类
 * 
 * @author tianmao
 *
 *         2015年6月10日 下午5:17:37
 */
public class PageUtil {
	private static Logger logger = Logger.getLogger(PageUtil.class);

	/**
	 * 分页
	 */
	public static String getPageBody(ResultDto results, String formId) {
		try {
			if (results == null || results.getResult() == null || results.getResult().isEmpty())
				return "";
			// 当前页
			int pageNo = results.getCurrentPageNo();
		
			// 每页显示数量
			int pageSize = results.getPageSize();
			// 总数
			long totalnum = results.getTotalRecords();
			// 总页数
			int totalPage = 0;
			if (totalnum > 0) {
				totalPage = (int) totalnum / pageSize;
				if (totalnum % pageSize > 0)
					totalPage++;
			}
			StringBuilder builder = new StringBuilder();
			builder.append("<div class='pull-right'>");
			builder.append("<div class='dataTables_paginate paging_bootstrap pagination'>");
			builder.append("<ul>");
			if (pageNo > 1) {
				int upPage = pageNo - 1;
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="+ generateJsMethod(1, formId) + "> 首 页 </a></li>");
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="+ generateJsMethod(upPage, formId) + ">上一页</a></li>");
			} else {
				builder.append("<li class='prev disabled'><a href='javascript:void(0)'>首页</a></li>");
				builder.append("<li class='prev disabled'><a href='javascript:void(0)'>上一页</a></li>");

			}
			
			if (totalPage < 10) {
				for (int i = 1; i <= totalPage; i++) {
					if (pageNo == i) {
						// 选择页面处理
						builder.append("<li class='prev disabled'><a href='javascript:void(0)' onclick="+ generateJsMethod(i, formId) + ">" + i + "</a></li>");
					} else {
						builder.append("<li class='next'><a href='javascript:void(0)' onclick="+ generateJsMethod(i, formId) + ">" + i + "</a></li>");
					}
				}
			} else if (pageNo < 5 && totalPage >= 10) {
				if (pageNo == 1) {
					builder.append("<li class='next disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(1, formId) + ">" + 1 + "</a></li>");
				} else {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(1, formId) + ">" + 1 + "</a></li>");
				}
				if (pageNo == 2) {
					builder.append("<li class='next disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(2, formId) + ">" + 2 + "</a></li>");
				} else {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(2, formId) + ">" + 2 + "</a></li>");
				}
				if (pageNo == 3) {
					builder.append("<li class='next disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(3, formId) + ">" + 3 + "</a></li>");
				} else {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(3, formId) + ">" + 3 + "</a></li>");
				}
				if (pageNo > 2) {
					if (pageNo == 4) {
						builder.append("<li class='next disabled'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(4, formId) + ">" + 4 + "</a></li>");
					} else {
						builder.append("<li class='next'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(4, formId) + ">" + 4 + "</a></li>");
					}
					if (pageNo == 5) {
						builder.append("<li class='next disabled'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(5, formId) + ">" + 5 + "</a></li>");
					} else {
						builder.append("<li class='next'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(5, formId) + ">" + 5 + "</a></li>");
					}

				}
				builder.append("<li class='prev disabled'><a href='javascript:void(0)'>...</a></li>");
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="
						+ generateJsMethod(totalPage - 2, formId) + ">" + (totalPage - 2) + "</a></li>");
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="
						+ generateJsMethod(totalPage - 1, formId) + ">" + (totalPage - 1) + "</a></li>");
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="
						+ generateJsMethod(totalPage, formId) + ">" + totalPage + "</a></li>");
			} else if (pageNo >= 5 && totalPage >= 10) {
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="
						+ generateJsMethod(1, formId) + ">" + 1 + "</a></li>");
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="
						+ generateJsMethod(2, formId) + ">" + 2 + "</a></li>");
				builder.append("<li class='prev disabled'><a href='javascript:void(0)'>...</a></li>");
				// int tpage=totalPage-4;
				if (pageNo < (totalPage - 4)) {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(pageNo - 1, formId) + ">" + (pageNo - 1) + "</a></li>");
					// 选择页面处理
					builder.append("<li class='prev disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(pageNo, formId) + ">" + (pageNo) + "</a></li>");
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(pageNo + 1, formId) + ">" + (pageNo + 1) + "</a></li>");
					builder.append("<li class='prev disabled'><a href='javascript:void(0)'>...</a></li>");
				} else if ((pageNo >= (totalPage - 4))) {
					if (pageNo == (totalPage - 5)) {
						builder.append("<li  class='prev disabled'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(totalPage - 5, formId) + ">" + (totalPage - 5) + "</a></li>");
					} else {
						builder.append("<li class='next'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(totalPage - 5, formId) + ">" + (totalPage - 5) + "</a></li>");
					}
					if (pageNo == (totalPage - 4)) {
						builder.append("<li  class='prev disabled'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(totalPage - 4, formId) + ">" + (totalPage - 4) + "</a></li>");
					} else {
						builder.append("<li class='next'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(totalPage - 4, formId) + ">" + (totalPage - 4) + "</a></li>");
					}
					if (pageNo == (totalPage - 3)) {
						builder.append("<li  class='prev disabled'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(totalPage - 3, formId) + ">" + (totalPage - 3) + "</a></li>");
					} else {
						builder.append("<li class='next'><a href='javascript:void(0)' onclick="
								+ generateJsMethod(totalPage - 3, formId) + ">" + (totalPage - 3) + "</a></li>");
					}
				}
				if (pageNo == (totalPage - 2)) {
					builder.append("<li  class='prev disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(totalPage - 2, formId) + ">" + (totalPage - 2) + "</a></li>");
				} else {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(totalPage - 2, formId) + ">" + (totalPage - 2) + "</a></li>");

				}
				if (pageNo == (totalPage - 1)) {
					builder.append("<li  class='prev disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(totalPage - 1, formId) + ">" + (totalPage - 1) + "</a></li>");
				} else {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(totalPage - 1, formId) + ">" + (totalPage - 1) + "</a></li>");
				}
				if (pageNo == totalPage) {
					builder.append("<li  class='prev disabled'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(totalPage, formId) + ">" + totalPage + "</a></li>");
				} else {
					builder.append("<li class='next'><a href='javascript:void(0)' onclick="
							+ generateJsMethod(totalPage, formId) + ">" + totalPage + "</a></li>");
				}
			}
			// buffer.append("<li class='active'><a href='#'>1</a></li><li><a
			// href='#'>2</a></li><li><a href='#'>3</a></li>");
			if (pageNo < totalPage) {
				int downPage = pageNo + 1;
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="+ generateJsMethod(downPage, formId) + ">下一页</a></li>");
				builder.append("<li class='next'><a href='javascript:void(0)' onclick="+ generateJsMethod(totalPage, formId) + "> 尾 页  </a></li>");
			} else {
				builder.append("<li class='prev disabled'><a href='javascript:void(0)'>下一页</a></li>");
				builder.append("<li class='prev disabled'><a href='javascript:void(0)'> 尾 页  </a></li>");
			}
			builder.append("<li  class='next disabled'><a>共" + totalnum + "条</a></li>");
			builder.append("</ul></div></div>");
			return builder.toString();
		} catch (Exception e) {
			logger.error("getFpageBody", e);
		}
		return "";
	}

	public static String generateJsMethod(int page, String formId) {
		return "do_search(" + page + ",'"+formId+"')";
	}

}

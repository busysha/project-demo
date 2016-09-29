<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="sidebar-nav">

  <c:forEach items="${_session_info.modules}" var="item">
  			<c:if test="${item.id == _session_info.currentParentMenu}">
  			</c:if>
  			
  			<a href="#setting-menu${item.id}" class="nav-header ${item.id != _session_info.currentParentMenu ? 'collapsed':''}" data-toggle="collapse"><i class="${item.icon}"></i>${item.name} <i class="icon-chevron-up"></i></a>
			<ul id="setting-menu${item.id}" class="nav nav-list ${item.id != _session_info.currentParentMenu ? 'collapse':''} " >
				<c:forEach items="${item.childs}" var="item2">
						<li style="margin-left:-20px;margin-right:20px;padding-left:20px;width:150%;  background-color:${item2.id == _session_info.currentMenu ? '#DEDEDE':'fff'};"><a href="javascript:void(0);" onclick="goModulePage('${item.id}','${item2.id }','${ctx}/${item2.url }');"><span >${item2.name}</span></a></li>
				</c:forEach> 
			</ul>
  </c:forEach>
 
 
 <%
	String pathl=request.getContextPath();
%>
  <script type="text/javascript">
  function goModulePage(pmoduleId,moduleId,url){ 
		$.ajax({
		  type: "POST",
		  url: "<%=pathl%>/record",
		  data: {moduleId:moduleId,
				 pmoduleId:pmoduleId
		  }, 
		  dataType: "json",
		  success: function(data){
			  //if(data.status==1){
	  			location.href=url;
			 // }
		  }
	});   
}
  </script>
 
</div>
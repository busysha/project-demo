<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="project" uri="http://www.xxx.com/project-tag"%>
<!DOCTYPE html>
<html>
<%@include file="../include/head.jsp"%>
<link rel="stylesheet" href="${ctx}/res/css/zTreeStyle.css" type="text/css">


<body class="">

	<%@include file="../include/nav.jsp"%>

	<%@include file="../include/leftMenu.jsp"%>
	
	<div class="content">

		<div class="header">
		
		<!-- 	<div class="stats">
    			<p class="stat"><button class="btn btn-danger"><i class="icon-save"></i> Save</button></p>
			</div> -->
			<h3 class="page-title">用户权限设置</h3>
		</div>
		
		<%@include file="../include/message.jsp"%>

		<div class="container-fluid">
			<div class="row-fluid">

				<section class="panel"> 
		                <div class="panel-body">
		                    <form:form id="userForm" class=" form-horizontal" action="${ctx}/user/roleset" method="post" modelAttribute="userBean" onsubmit="return toVaild()">
		                        <div class="form-group">
		                            <label class="col-sm-2 col-lg-2 control-label">用户名: </label>
		                            <div class="col-sm-4 col-lg-4">
		                           		 <form:input type="hidden" id="id"  class="form-control"   path="id" />
		                                <form:input type="text" id="loginName"  class="form-control"   path="loginName" />
		                                <span class="help-block"></span>
		                            </div>
		                        </div>
		                        
		                        
		                        <div class="form-group">
		                            <label class="col-sm-2 col-lg-2 control-label">真实姓名： </label>
		                            <div class="col-sm-4 col-lg-4">
		                                <form:input type="text" id="realName"  class="form-control"   path="realName" />
		                                <span class="help-block"></span>
		                            </div>
		                        </div>
		                        
		                         <div class="form-group">
		                            <label class="col-sm-2 col-lg-2 control-label">角色设置</label>
		                            <br/><br/>
		                            <div class="col-sm-4 col-lg-4">
		                            	<form:input type="hidden" id="roIds" class="form-control"  path="roIds" />
		                            	 <div class="zTreeDemoBackground left" style="padding-left:10%">
											<ul id="treeDemo" class="ztree"></ul>
										</div>
		                            </div>
		                        </div>
		                        
		                        <button type="submit" class="btn btn-info checkDirty">保存</button>
		                        <a href="javascript:history.back();"><button type="button" class="btn btn-warning" >取消</button></a>
		                    </form:form>
		                </div>
		            </section>

			</div>
		</div>
	</div>
	
	

	<%@include file="../include/jsInclude.jsp"%>

	<script type="text/javascript" src="${ctx}/res/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/res/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};


var zNodes =<%=request.getAttribute("roleNotes")%>;

	
$().ready(function(){
	
	
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	 setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
});


	 
function toVaild(){
	 var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	 nodes = zTree.getCheckedNodes(true),
	 v = "";
	 for(var i=0;i<nodes.length;i++){
		 v+=nodes[i]["id"]+","
	 }
	$("#roIds").val(v);
	return true;
}

</script>

</body>
</html>



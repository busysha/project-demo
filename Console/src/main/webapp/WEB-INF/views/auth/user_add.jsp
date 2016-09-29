<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="project" uri="http://www.xxx.com/project-tag"%>
<!DOCTYPE html>
<html>
<%@include file="../include/head.jsp"%>

<body class="">

	<%@include file="../include/nav.jsp"%>

	<%@include file="../include/leftMenu.jsp"%>
	
	<div class="content">

		<div class="header">
		
		<!-- 	<div class="stats">
    			<p class="stat"><button class="btn btn-danger"><i class="icon-save"></i> Save</button></p>
			</div> -->
			<h3 class="page-title">用户管理</h3>
		</div>
		
		<%@include file="../include/message.jsp"%>

		<div class="container-fluid">
			<div class="row-fluid">

				<section class="panel"> 
		                <div class="panel-body">
		                    <form:form id="userForm" class=" form-horizontal" action="${ctx}/user/add" method="post" modelAttribute="userBean">
		                        <div class="form-group">
		                            <label class="col-sm-2 col-lg-2 control-label">用户名: </label>
		                            <div class="col-sm-4 col-lg-4">
		                                <form:input type="text" id="loginName"  class="form-control"   path="loginName" />
		                                <span class="help-block"></span>
		                            </div>
		                        </div>
		                        
		                        <div class="form-group">
		                            <label class="col-sm-2 col-lg-2 control-label">密码： </label>
		                            <div class="col-sm-4 col-lg-4">
		                                <form:input type="text" id="password"  class="form-control"   path="password" />
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
		                            <label class="col-sm-2 col-lg-2 control-label">电话号码： </label>
		                            <div class="col-sm-4 col-lg-4">
		                                <form:input type="text" id="mobile"  class="form-control"   path="mobile" />
		                                <span class="help-block"></span>
		                            </div>
		                        </div>
		                        
		                        <div class="form-group">
		                            <label class="col-sm-2 col-lg-2 control-label">EMAIL： </label>
		                            <div class="col-sm-4 col-lg-4">
		                                <form:input type="text" id="email"  class="form-control"   path="email" />
		                                <span class="help-block"></span>
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

	<script>
	
	</script>

</body>
</html>



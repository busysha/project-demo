<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="project" uri="http://www.xxx.com/project-tag"%>
<!DOCTYPE html>
<html>
<%@include file="./include/head.jsp" %>
<script type="text/javascript" src="${ctx}/res/js/jquery.validate.min.js"></script>
<script src="http://www.veryhuo.com/uploads/Common/js/jQuery.md5.js"></script>

<style>

#loginForm .error{
	font-size: 10px;
	color: red;
}

</style>

<body class="">
	
	<%@include file="./include/nav.jsp" %>
	<%@include file="./include/message.jsp" %>
	<div class="row-fluid">
	    <div class="dialog">
	        <div class="block">
	            <p class="block-heading">CMS登录</p>
	            <div class="block-body">
	                <form:form id="loginForm" action="${ctx}/auth/login" method="post" modelAttribute="loginBean">
	                	<project:token></project:token>
	                    <label>用户名</label>
	                    <form:input type="text" id="loginName" class="span12" path="loginName" />
	                    <label>密码</label>
	                    <form:input id="password" type="password" class="span12" path="password" />
	                    <button type="submit" class="btn btn-primary pull-right">登 录</button>
	                    <div class="clearfix"></div>
	                </form:form>
	            </div>
	        </div>
	    </div>
	</div>

	<%@include file="./include/jsInclude.jsp" %>
	
	<script type="text/javascript">
  	$().ready(function(){
  		$("#password").val("");
  		$("#loginForm").validate({
  	        rules: {
  	        	loginName: {
  	        		required:true,
  	        		maxlength:100
  	        	},
  	        	password:{
  	        		required:true,
  	        		maxlength:20
  	        	}
  	        },
  	        messages: {
  	        	loginName: "请输入正确的用户名",
  	        	password:"请输入正确的密码"
  	        },
  	        submitHandler: function(form) { 
  	        	$("#password").val($.md5($("#password").val()));
  	     	    form.submit();
  			}
  	    });
  	});

  </script>
</body>
</html>



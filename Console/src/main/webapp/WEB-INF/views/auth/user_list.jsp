<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="project" uri="http://www.xxx.com/project-tag"%>
<!DOCTYPE html>
<html>
<%@include file="../include/head.jsp"%>

<body class="">

	<%@include file="../include/nav.jsp"%>

	<%@include file="../include/leftMenu.jsp"%>
	<div id="app">
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

				<div class="row-fluid">
					<div class="block span12">
						<a href="#query" class="block-heading" data-toggle="collapse">查询</a>
						<div id="query" class="block-body collapse in" >
							<form:form action="${ctx}/user/list" id="queryForm" class="marging-top-5" modelAttribute="userSearchDto">
								
								<form:input type="hidden"  class="input-xlarge" path="pageNo" />
								<label>登录名</label>
								<form:input type="text"  class="input-xlarge" path="loginName" />
								<label>手机号码</label>
								<form:input type="text"  class="input-xlarge" path="phone" />
								<label>用户状态</label>
								<form:select path="status" >
									<form:option value="">[选择状态]</form:option>
									<form:option value="A">可用</form:option>
									<form:option value="I">禁用</form:option>
								</form:select>
								<div>
									<button class="btn btn-primary"><i class="icon-search"></i>查询</button>
								    <a href="${ctx}/user/goadd"><button type="button" class="btn btn-success btn-sm"><i class="icon-plus-sign"></i> 添加用户</button></a>
								</div>
							</form:form>
						</div>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="block span12">
						<a href="#tablewidget" class="block-heading"
							data-toggle="collapse">用户列表</a>
						<div id="tablewidget" class="block-body collapse in">
							<table class="table">
								<thead>
									<tr>
										<th>用户编号</th>
										<th>用户名</th>
										<th>真实姓名</th>
										<th>电话号码</th>
										<th>邮箱</th>
										<th>创建日期</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${users.result}" var="item">
										<tr>
											<td>${item.id}</td>
											<td>${item.loginName}</td>
											<td>${item.realName}</td>
											<td>${item.mobile}</td>
											<td>${item.email}</td>
											<td>${item.time}</td>
											<td>
											<c:if test="${item.status=='A'}">
												 <c:out value="正常"></c:out>
											</c:if>
												<c:if test="${item.status!='A'}">
												 <span style="color:red"><c:out value="禁用"></c:out></span>
											</c:if>
											
											</td>
											<td>
											<a href="${ctx }/user/goroleset?userId=${item.id}"><button class="btn btn-info">角色设置</button></a>
											 <c:choose>
                                                    <c:when test="${item.status == 'A'}"><button class="btn btn-info" @click="orgInactive('${item.id}',$event)">禁用</button></c:when>
                                                    <c:when test="${item.status == 'I'}"><button class="btn btn-danger" @click="orgActive('${item.id}',$event)">恢复</button></c:when>
                                              </c:choose>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p>
								<project:pagination resultKey="users" formId="queryForm"></project:pagination>
							</p>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	
	</div>

	<%@include file="../include/jsInclude.jsp"%>
<script type="text/javascript" src="http://cdn.bootcss.com/vue-validator/2.1.7/vue-validator.js"></script>

	<script>
	var app = new Vue({
	    el:"#app",
	    data:{
	        item:{
	            descriptions:{}
	        },
	        newOrg:{}
	    },
	    methods:{
	        xhrRequest:function(url,items,callback){
	            $.ajax({
	                type: "POST",
	                url: url,
	                data: items,
	                success: function(obj){
	                    callback(obj);
	                }
	            });
	        },
	        orgActive:function(id,event){
	            if(confirm("确定恢复吗?")){
	                var _self = this;
	                _self.xhrRequest("${ctx}/user/active",{'id':id},function(e){
	                    if(e.code == 1){
	                    	alert("恢复成功");
	                    	history.go(0) ;
	                    }else{
	                        alert("恢复成功,请重新尝试");
	                    }
	                });
	            }
	        },
	        orgInactive:function(id,event){
	            if(confirm("确定禁用吗?")){
	                var _self = this;
	                _self.xhrRequest("${ctx}/user/inactive",{'id':id},function(e){
	                    if(e.code == 1){
	                        alert("禁用成功");
	                        history.go(0) ;
	                    }else{
	                        alert("禁用失败,请重新尝试");
	                    }
	                });
	            }
	        }
	    }
	});
	</script>

</body>
</html>



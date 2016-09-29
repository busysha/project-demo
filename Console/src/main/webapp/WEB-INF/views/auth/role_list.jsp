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
			<h3 class="page-title">角色管理</h3>
		</div>
		
		<%@include file="../include/message.jsp"%>

		<div class="container-fluid">
			<div class="row-fluid">

				<div class="row-fluid">
					<div class="block span12">
						<a href="#query" class="block-heading" data-toggle="collapse">查询</a>
						<div id="query" class="block-body collapse in" >
							<form:form action="${ctx}/role/list" id="queryForm" class="marging-top-5" modelAttribute="roleSearchDto">
								
								<form:input type="hidden"  class="input-xlarge" path="pageNo" />
								<label>角色名称</label>
								<form:input type="text"  class="input-xlarge" path="name" />
								<div>
									<button class="btn btn-primary"><i class="icon-search"></i>查询</button>
								    <a href="${ctx}/role/goadd"><button type="button" class="btn btn-success btn-sm"><i class="icon-plus-sign"></i> 添加角色</button></a>
								</div>
							</form:form>
						</div>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="block span12">
						<a href="#tablewidget" class="block-heading"
							data-toggle="collapse">角色列表</a>
						<div id="tablewidget" class="block-body collapse in">
							<table class="table">
								<thead>
									<tr>
										<th>用户编号</th>
										<th>角色名称</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${roles.result}" var="item">
										<tr>
											<td>${item.roleId}</td>
											<td>${item.roleName}</td>
											<td><a href="${ctx }/role/goprivilegeset?roleId=${item.roleId}"><button class="btn btn-info">权限设置</button></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p>
								<project:pagination resultKey="roles" formId="queryForm"></project:pagination>
							</p>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	
	

	<%@include file="../include/jsInclude.jsp"%>

	<script>
	
	</script>

</body>
</html>



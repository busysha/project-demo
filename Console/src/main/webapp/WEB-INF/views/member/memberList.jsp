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

				<div class="row-fluid">
					<div class="block span12">
						<a href="#query" class="block-heading" data-toggle="collapse">查询</a>
						<div id="query" class="block-body collapse in" >
							<form:form action="${ctx}/member/list" id="queryForm" class="marging-top-5" modelAttribute="memberQueryBean">
								<input type="hidden" name="pageNo" value="0" >
								<label>邦内编号</label>
								<form:input type="text" value="" class="input-xlarge" path="id" />
								<label>用户昵称</label>
								<form:input type="text" value="" class="input-xlarge" path="nickName" />
								<label>用户状态</label>
								<form:select path="status" >
									<form:option value="">[选择状态]</form:option>
									<form:option value="A">可用</form:option>
									<form:option value="I">禁用</form:option>
								</form:select>
								<div>
									<button class="btn btn-primary"><i class="icon-search"></i>查询</button>
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
										<th>邦内编号</th>
										<th>用户昵称</th>
										<th>手机号</th>
										<th>性别</th>
										<th>年龄</th>
										<th>注册来源</th>
										<th>用户类型</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${members.result}" var="item">
										<tr>
											<td>${item.id}</td>
											<td><a href="#"><img width="40px"
													src="${item.avatarImg}" /> &nbsp; ${item.nickname}</a></td>
											<td>${item.mobile}</td>
											<td><c:choose>
													<c:when test="${item.gender == 'F'}">女</c:when>
													<c:when test="${item.gender == 'M'}">男</c:when>
												</c:choose></td>
											<td>${item.age}</td>
											<td>${item.channel}</td>
											<td><c:choose>
													<c:when test="${item.type == 'A'}">
														<span class="green">个人认证</span>
													</c:when>
													<c:when test="${item.type == 'O'}">
														<span class="orange">组织认证</span>
													</c:when>
													<c:otherwise>
														<span>普通用户</span>
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${item.status == 'A'}">
														<span class="green">可用</span>
													</c:when>
													<c:when test="${item.status == 'I'}">
														<span class="red">禁用</span>
													</c:when>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${item.status == 'A'}">
														<a href="#memberModal" onclick="showModal(${item.id},'inactive','${item.nickname}')" data-toggle="modal" class="btn">禁用</a>
													</c:when>
													<c:when test="${item.status == 'I'}">
														<a href="#memberModal" onclick="showModal(${item.id},'active','${item.nickname}')" data-toggle="modal" class="btn">启用</a>
													</c:when>
												</c:choose></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<p>
								<project:pagination resultKey="members" formId="queryForm"></project:pagination>
							</p>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	
	<div class="modal small hide fade" id="memberModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-header">
    		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    		<h3 id="myModalLabel">提示</h3>
  		</div>
  		<div class="modal-body">
    		<p class="error-text"><i class="icon-warning-sign modal-icon"></i><span id="modal_content">您确定禁用此用户?</span></p>
  		</div>
  		<div class="modal-footer">
    		<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    		<button id="update_member_status_btn" class="btn btn-danger" data-dismiss="modal">确定</button>
  		</div>
	</div>
	
	

	<%@include file="../include/jsInclude.jsp"%>

	<script>
	
		function showModal(id,action,name){
			if('active' == action){
				$('#modal_content').html('您确定启用 "' + name + '" ?');
				$('#update_member_status_btn').click(function(){
					window.location.href = '${ctx}/member/status?mid='+id+'&action=active';
					
				});
			}else{
				$('#modal_content').html('您确定禁用 "' + name + '" ?');
				$('#update_member_status_btn').click(function(){
					window.location.href = '${ctx}/member/status?mid='+id+'&action=inactive';
				});
			}
			//$('#memberModal').show();
			
		}
	</script>

</body>
</html>



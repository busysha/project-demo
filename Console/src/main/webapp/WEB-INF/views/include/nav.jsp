<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<div class="navbar">
	<div class="navbar-inner">
		<c:if test="${_session_info.user != null}">
			<ul class="nav pull-right">
				<li id="fat-menu" class="dropdown">
					<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-user"></i> ${_session_info.user.realName} <i class="icon-caret-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li><a tabindex="-1" href="#">修改密码</a></li>
						<li class="divider"></li>
						<li><a tabindex="-1" href="${ctx}/auth/logout">退出</a></li>
					</ul></li>
			</ul>
		</c:if>
		
		<a class="brand" href="#"><span class="first">DEMO</span> <span
			class="second">CMS</span></a>
	</div>
</div>
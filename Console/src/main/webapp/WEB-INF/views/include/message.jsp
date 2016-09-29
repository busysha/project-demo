<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>

<c:if test="${ not empty _info_message }">
<div class="row-fluid">
<div class="alert alert-info">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <i class="icon-exclamation-sign"></i>&nbsp;&nbsp;<c:out value="${_info_message}"></c:out>
</div>
</div>
</c:if>

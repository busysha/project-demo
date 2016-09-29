<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
	String path = request.getContextPath();
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<head>
<title>DEMO</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">

<link rel="stylesheet" type="text/css" href="${ctx}/res/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${ctx}/res/css/theme.css">
<link rel="stylesheet" href="${ctx}/res/lib/font-awesome/css/font-awesome.css">

<script src="http://cdn.bootcss.com/vue/1.0.26/vue.js"></script>
<script src="http://7xkbol.com2.z0.glb.qiniucdn.com/jquery.min.js"></script>


<script src="${ctx}/res/assets/bootstrapValidator/js/bootstrapValidator.min.js"></script>
<link href="${ctx}/res/assets/bootstrapValidator/css/bootstrapValidator.min.css" rel="stylesheet" />

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="${ctx}/res/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${ctx}/res/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${ctx}/res/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${ctx}/res/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="${ctx}/res/assets/ico/apple-touch-icon-57-precomposed.png">

<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8 "><![endif]-->
<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<!--<![endif]-->

</head>
<script type="text/javascript">
    if( typeof Array.prototype.indexOf !== 'function' ){
        alert("请使用>IE8的浏览器访问,或者直接去下载Chrome");
        window.location.href = "http://down.tech.sina.com.cn/content/40975.html";
    }
</script>
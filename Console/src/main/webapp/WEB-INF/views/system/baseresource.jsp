<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/9/26
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@include file="../include/head.jsp"%>
</head>
<style type="text/css">
    .info-hover{
        width: 15%;
    }
</style>
<body>
    <%@include file="../include/nav.jsp"%>
    <%@include file="../include/leftMenu.jsp"%>
    <div id="app">
        <div class="content">
            <div class="header">
                <!-- 	<div class="stats">
                        <p class="stat"><button class="btn btn-danger"><i class="icon-save"></i> Save</button></p>
                    </div> -->
                <h3 class="page-title">app基础数据<span class="btn-danger">(请谨慎修改)</span></h3>
            </div>

            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="row-fluid">
                        <div class="block span12">
                            <div class="row-fluid">
                                <div class="span12">
                                    <a href="#" class="block-heading" data-toggle="collapse">申请列表</a>
                                    <table class="table">
                                        <tr>
                                            <th>设置描述</th>
                                            <th>设置默认值</th>
                                            <th>操作</th>
                                        </tr>
                                        <c:forEach items="${res}"  var="item">
                                            <tr>
                                                <td>
                                                        ${item.descriptions}
                                                </td>
                                                <td class="info-hover">
                                                    <span style="color: #4bb10d;">${item.value}</span>
                                                </td>
                                                <td>
                                                    <button class="icon-edit btn-inverse" data-toggle="modal" data-target="#updateModal" @click="configInfo('${item.id}')">修改</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- update modal -->
        <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">更新设置</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div>设置描述<input :value="item.descriptions" v-model="updateItem.descriptions" type="text" readonly/></div>
                            <div>设置默认值<textarea :value="item.value" v-model="updateItem.value" type="text" style="min-height:200px;min-width: 300px">
                            {{item.value}}
                            </textarea></div>
                            <input :value="item.id" v-model="updateItem.id" type="hidden"/>
                            <input :value="item.key" v-model="updateItem.key" type="hidden"/>
                            <input :value="item.isCanChange" v-model="updateItem.isCanChange" type="hidden"/>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal" @click="configUpdate()">保存</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal" >关闭</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="../include/jsInclude.jsp"%>
<script type="text/javascript">
    var app = new Vue({
        el: "#app",
        data: {
            item: {},
            updateItem:{}
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
            configInfo:function(id){
                var _self = this;
                _self.xhrRequest("${ctx}/system/info",{'id':id},function(e){
                    _self.item = e;
                    console.log(e);
                });
            },
            configUpdate:function(){
                var _self = this;
                var obj = JSON.stringify(this.updateItem);
                _self.xhrRequest("${ctx}/system/update",'source=' + obj,function(e){
                    location.reload();
                });
            }
        }

    });
</script>
</body>
</html>

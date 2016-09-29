
function menu_click(ctx,menuId,url){

	$.ajax({
		url: ctx+"/menu",
		type : "GET",
		dataType : "json",
		data : {"menu": menuId},
		success : function(){
			
			window.location.href = url;
		}
	});
	
}


function do_search(pageNo,formId){
	$("#"+formId+" input[name='pageNo']").val(pageNo);
	$("#"+formId).submit();
}

function do_delete(url){
	var msg='确定删除?';
	if(confirm(msg)){
		window.location.href=url;
	}
}

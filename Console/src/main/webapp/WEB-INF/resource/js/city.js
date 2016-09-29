$().ready(function(){
	$('#proviceSelect').chosen({
    	no_results_text : "未找到此选项!", 
    	width:"100%"
    });
	$('#proviceCity').chosen({
    	no_results_text : "未找到此选项!", 
    	width:"100%"
    });
});

//选择好省份进行，市关联	
$('#proviceSelect').on('change', function(evt, params) {
	initCitySelect(params.selected,null);
});

//初始化provice select
function initProviceSelect(val){
	$("#proviceSelect").find("option").remove();
	$("#proviceSelect").append("<option value=''></option>");
	$.ajax({
		type: "get",
		url: ctx+"/system/getAllProvice",
		dataType: "json",
		success: function(result){
			$.each(result.provinces,function(n,obj){
				if(obj.id==val){
					$("#proviceSelect").append("<option value='"+obj.id+"' selected>"+obj.regionName+"</option>");
				}else{
					$("#proviceSelect").append("<option value='"+obj.id+"'>"+obj.regionName+"</option>");
				}
			});
			$("#proviceSelect").trigger("chosen:updated"); 
		}
	});
}
//初始化市
function initCitySelect(proviceId,selectId){
	$.ajax({
		type: "get",
		url: ctx+"/system/getCityByProvinceId",
		dataType: "json",
		data:{provinceId:proviceId},
		success: function(result){
			$("#proviceCity").find("option").remove();
			$("#proviceCity").append("<option value=''></option>");
			$.each(result.citys,function(n,obj){
				if(obj.id==selectId){
					$("#proviceCity").append("<option value='"+obj.id+"' selected>"+obj.regionName+"</option>");
				}else{
					$("#proviceCity").append("<option value='"+obj.id+"'>"+obj.regionName+"</option>");
				}
			});
			$("#proviceCity").trigger("chosen:updated"); 
		},
	});
}
$package('YiYa.Message');
YiYa.Message = function(){
	var _box = null;
	var _this = {
		config:{
  			dataGrid:{
  				title:'短信消费列表',
	   			url:'/web/message_list',
	   			columns:[[
						{field:'id',title:'编号'},
						{field:'content',title:'内容',width:80,sortable:true},
						{field:'receNumber',title:'接收号码',width:120,sortable:true},
						{field:'dateStr',title:'发送日期',width:120,sortable:true},
						{field:'sendStatus',title:'发送状态',width:40,sortable:true},
						{field:'callbackStatus',title:'回调状态',width:40,sortable:true},
						{field:'errorMsg',title:'错误信息',width:120,sortable:true},
						{field:'categoryId',title:'科室编码',width:80,sortable:true},
						{field:'appId',title:'应用ID',width:40,sortable:true},
						{field:'counts',title:'条数',width:40,sortable:true},
						{field:'createStr',title:'创建时间',width:120,sortable:true}
				]],
				toolbar:[{}
						]
			}
		},
		init:function(){
			_box = new YDataGrid(_this.config); 
			_box.init();
		}
	}
	return _this;
}();

$(function(){
	YiYa.Message.init();
});		
$package('YiYa.UserRole');
YiYa.UserRole = function(){
	var _box = null;
	var _this = {
		config:{
			action:{
				save:'/web/category_save',
				getId:'/web/get_category_id',
				remove:'/web/category_delete'
			},
  			dataGrid:{
  				title:'科室列表',
	   			url:'/web/category_list',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'idStr',title:'登陆编号',width:120,sortable:true},
						{field:'categoryName',title:'科室名称',width:80,sortable:true},
						{field:'delete',title:'状态',width:80,align:'center',sortable:true,styler:function(value,row,index){
							if(value == 1){
							  return 'color:red;';  
							}
						},
						formatter:function(value,row,index){
							if(value == 0){
								return "可用";
							}
							if(value == 1){
								return "禁用";
							}
						}},
						{field:'createStr',title:'创建时间',width:120,sortable:true},
				]],
				toolbar:[
					{id:'btnadd',text:'Add',btnType:'add'},
					{id:'btndelete',text:'Delete',btnType:'remove'}
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
	YiYa.UserRole.init();
});		
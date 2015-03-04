$package('YiYa.Project');
YiYa.Project = function(){
	var _box = null;
	var _this = {
		config:{
			action:{
				save:'/web/project_save',
				remove:'/web/project_delete'
			},
  			dataGrid:{
  				title:'项目列表',
	   			url:'/web/project_list',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'idStr',title:'编号',width:40},
						{field:'projectName',title:'项目名称',width:80,sortable:true},
						{field:'userName',title:'发送账号',width:80,sortable:true},
						{field:'maxLength',title:'最大长度',width:120,sortable:true},
						{field:'overPlus',title:'剩余条数',width:120,sortable:true},
						{field:'deleted',title:'删除',width:40,sortable:true,styler:function(value,row,index){
						if(value == 1){
							  return 'color:red;';  
							}
						},
						formatter:function(value,row,index){
							if(value == 0){
								return "";
							}
							if(value == 1){
								return "已删除";
							}
						}},
						{field:'categoryId',title:'科室编码',width:40,sortable:true},
						{field:'userPhone',title:'联系人手机',width:120,sortable:true},
						{field:'createStr',title:'创建时间',width:120,sortable:true},
						{field:'user',title:'联系人姓名',width:80,sortable:true}
				]],
				toolbar:[{id:'btnadd',text:'Add',btnType:'add'},
				{id:'btndelete',text:'Delete',btnType:'remove'}]
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
	YiYa.Project.init();
});		
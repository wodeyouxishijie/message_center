$package('YiYa.Template');
YiYa.Template = function(){
	var _box = null;
	var _this = {
		config:{
			updateTemplate:'/web/update_template',
			action:{
				save:'/web/template_add',
				remove:'/web/template_delete',
				getId:'/web/template_get'
			},
  			dataGrid:{
  				title:'项目列表',
	   			url:'/web/template_list',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'idStr',title:'模板ID',width:80,sortable:true},
						{field:'name',title:'模板名称',width:120,sortable:true},
						{field:'projectId',title:'项目编码',width:80,sortable:true},
						{field:'paramList',title:'参数列表',width:120,sortable:true},
						{field:'templateString',title:'模板内容',width:400,sortable:true}
				]],
				toolbar:[{id:'btnadd',text:'Add',btnType:'add'},
				         {id:'btnedit',text:'Edit',btnType:'edit'},
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
	YiYa.Template.init();
});		
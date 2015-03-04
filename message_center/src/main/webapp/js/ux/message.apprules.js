$package('YiYa.Rule');
YiYa.Rule = function(){
	var _box = null;
	var _this = {
		config:{
			action:{
				save:'/web/rules_save',
				remove:'/web/rules_delete'
			},
  			dataGrid:{
  				title:'项目列表',
	   			url:'/web/rule_list',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'appId',title:'项目ID',width:80,sortable:true},
						{field:'frequencyType',title:'频率',width:120,sortable:true,
						formatter:function(value,row,index){
							if(value == 3){
								return "每天";
							} else if(value == 2){
								return "每周";
							} else if(value == 1) {
								return "每月";
							}
						}},
						{field:'max',title:'最大条数',width:120,sortable:true}
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
	YiYa.Rule.init();
});		
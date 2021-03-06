$package('YiYa.login');
YiYa.login = function(){
	return {
		toLogin:function(){
			try{
				var form = $("#loginForm");
				if(form.form('validate')){
					YiYa.progress('Please waiting','Loading...');
					YiYa.submitForm(form,function(data){
						YiYa.closeProgress();
						if(data.success){
					 		window.location= "/web/index";
				        }else{
				       	   YiYa.alert('提示',data.msg,'error');  
				        }
				        YiYa.login.loadVrifyCode();//刷新验证码
					});
				}
			}catch(e){
				
			}
			return false;
		},
		loadVrifyCode:function(){//刷新验证码
			var _url = "/captcha?time="+Math.random();
			$(".vc-pic").attr('src',_url);
		},
		init:function(){
			if(window.top != window.self){
				window.top.location =  window.self.location;
			}
			//验证码图片绑定点击事件
			$(".vc-pic").click(YiYa.login.loadVrifyCode);
			
			var form = $("#loginForm");
			form.submit(YiYa.login.toLogin);
		}
	}
}();

$(function(){
	YiYa.login.init();
});		
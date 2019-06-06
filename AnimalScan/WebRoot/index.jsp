<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title>刷脸登录</title>
		<style>
			*{margin: 0; padding: 0;}
			html,body{ width: 100%; height: 100%;overflow: hidden;}
			body{ background-image: url(img/bg.jpg);background-repeat: no-repeat;}
			.h1{color: yellowgreen; size: a4;margin-left: 500px;}
			 h3{color:red;margin:auto;}
			.media{width: 634px; height: 500px; margin:10px auto 0;position: relative;overflow: hidden;}
			#canvas{ display: none;}
			#scan{position: absolute;width: 100%;height: 100%;background: url(img/scan.png);background-size: cover;} 
			.btn{width: 200px;height: 50px;text-align: center;line-height: 50px;margin: 20px auto 0;background:#00f1ff ;color: #fff;cursor: pointer;border-radius: 40px;}
		</style>
	</head>
	<body>
		<center>
			<h1>把脸伸过来刷一刷脸</h1><br/>
			<h3></h3>
		</center>
		<div class="media">
			<video src="" width="634" height="500" id="video" autoplay></video><!--video 播放 autoplay自动播放-->
			<canvas id="canvas" width="634" height="500"></canvas>
			<div id="scan"></div>
		</div>
		<div class="btn">登录</div>
		<script src="js/jquery.min.js"></script>
		<!--script 网页脚本文件-->
		<script type="text/javascript">
		//获取video标签对象
			var video = document.getElementById("video");//var 声明
			var context = canvas.getContext("2d");
			
			var getUserMedia = (navigator.getUserMedia || navigator.webkitGetUserMedia() ||navigator.mozGetUserMedia() ||navigator.msGetUserMedia())
			//浏览器自带用户媒体对象    . 的      默认       谷歌      火狐     IE  (兼容性写法)        (方法)
			//调用console.log(getUserMedia);
			//调用摄像头						
			getUserMedia.call(navigator,{video:true,audio:false},function(localMediaStream){
				//调用摄像头的媒体数据传递给video标签对象
				video.src = window.URL.createObjectURL(localMediaStream)
			},function(e){
				//如果获取摄像头失败，则在控制台提示失败信息
				console.log("获取摄像头失败",e);
			});
			function login(){
				scan();
				context.drawImage(video,0,0,634,500);//横纵坐标 宽高
				var imgSrc = document.getElementById("canvas").toDataURL("image/png");
			 	//alert(imgSrc);
				var base64 = imgSrc.split("base64,")[1];
				$("h3").html("请耐心等待...");
				var t=setInterval(function(){
					$("h3").append("..");
				}, 2000);
				
				$.ajax({
					//参数传递地址
					url:"faceLogin",
					//参数传递方式
					type:"post",
					//要传递到后台的参数
					data:{"base64":base64},
					//回调函数
					success:function(data){
						clearInterval(t);
						//alert(data);
						if(eval(data)){
							window.location.href="animal/index.html?userName=unique";
						}else{
							$("h3").html("检测失败,请重新再试!");
						}
					}
				});
			}
			document.getElementsByClassName('btn')[0].onclick = function(){
				login();
			}
			function scan(){
				var box = $(".media");
				$("#scan").css({"bottom":box.height()}).animate({bottom:0},2000,function(){$(this).css({"bottom":box.height()})});
			}
		</script>
	</body>
</html>
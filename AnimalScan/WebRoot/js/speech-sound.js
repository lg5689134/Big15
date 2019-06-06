//语音提示可以使用浏览器自带功能比较简单
/* var msg=new  SpeechSynthesisUtterance("请确认登录");
window.speechSynthesis.speak(msg); */
function speak(text){
  if ('speechSynthesis' in window) {
    var sentence = new SpeechSynthesisUtterance();
    var voices = window.speechSynthesis.getVoices();
    for(var i = 0; i < voices.length; i++) {
          if(voices[i]['name'] == "Alex"){
            sentence.voice = voices[i];
          }
      }
    sentence.pitch = 1;//音调高低
    sentence.rate = 0.7;//播放语速
   //sentence.volume = 0.5; //播放音量
    sentence.text = text;
    window.speechSynthesis.speak(sentence);
  } else {
    console.log("对不起! 你当前浏览器暂不支持 SpeechSynthesis.");
  }
}

/* 兼容微信及各种常见的浏览器
 * tex 必填，合成的文本，使用UTF-8编码。小于2048个中文字或者英文数字
 * tok 必填，开放平台获取到的开发者access_token,在使用前请更换自己的token，具体请参考https://ai.baidu.com/docs 获取token
 * cuid必填，用户唯一标识，机器 MAC 地址或 IMEI 码，长度为60字符以内
 * ctp必填，客户端类型选择，web端填写固定值1
 * lan必填，固定值zh
 * per 发音人选择, 0为普通女声，1为普通男生，3为情感合成-度逍遥，4为情感合成-度丫丫
 * vol音量，取值0-15，默认为5中音量
 * 
 * */	 	
function sound(str){
	var obj=document.createElement("embed");
	obj.style.width=0;
	obj.style.height=0;
	obj.src="http://tsn.baidu.com/text2audio?lan=zh&cuid=4545656565&per=0&ctp=1&vol=9&tok=24.d38e14377e1f54afe309b29c14467ed0.2592000.1559180044.282335-15826305&tex="+str;
	obj.loop=0;
	document.body.appendChild(obj);
}
		
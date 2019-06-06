package com.byzx.face;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;

/**
 * 语音服务功能核心类
 */
public class SampleClient {

    //设置APPID/AK/SK 
    public static final String APP_ID = "15871052";
    public static final String API_KEY = "tqDlru9AERM5USZGQTkle6Xu";
    public static final String SECRET_KEY = "Wp51Aa8Fl6TbgXNmdNtZAk8EpSw6428C";

    // 初始化一个FaceClient
    AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
    
    
    public static void main(String[] args) {
    	new SampleClient().getSynthesis("D:/speech/unique.wav");
    	
	}
    /**
     * 语音识别
     * @param fileName
     * @return
     */
    public String getSynthesis(String filePathName){
  
    	 // 可选：设置网络连接参数
    	client.setConnectionTimeoutInMillis(2000); //建立连接的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000); //通过打开的连接传输数据的超时时间（单位：毫秒）
        // 对本地语音文件进行识别G:\voice
//        String path = "F:\\testvoice\\"+fileName;
        JSONObject asrRes = client.asr(filePathName, "wav", 8000, null);
        String Rtext = asrRes.optString("result");
        System.out.println(Rtext);
        String[] ss = null;
        if(Rtext.contains("，")){
        	ss=Rtext.split("，");
        }else if(Rtext.contains("？")){
        	ss=Rtext.split("？");
        }else if(Rtext.contains("！")){
        	ss=Rtext.split("！");
        }else{
        	return Rtext;
        }
        String b="";
        for (int i = 0; i < ss.length-1; i++) {
            String a ="";
        	if(i==0){
        		a = ss[i].substring(2) ;
 //     		System.out.println(ss[i].substring(2));
        	}else{
        		a = ss[i];
 //       		System.out.println(ss[i]);
        	}
        	b = b + a;
		}
        return b;
        
    }
    
    
    
}

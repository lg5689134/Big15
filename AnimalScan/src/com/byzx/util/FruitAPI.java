/**
 * @filename FruitAPI.java
 * @author lg
 * @date 2019年4月2日 下午4:13:39
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.util;

import java.net.URLEncoder;
import org.json.JSONObject;

public class FruitAPI {
	public static String fruitAPI(String image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/classify/ingredient";
        try {
            // 本地文件路径
        	//String image = "D:/fruit/2.jpg";
            byte[] imgData = FileUtil.readFileByBytes(image);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.d38e14377e1f54afe309b29c14467ed0.2592000.1559180044.282335-15826305";
            //AuthService.getAuth("tqDlru9AERM5USZGQTkle6Xu", "Wp51Aa8Fl6TbgXNmdNtZAk8EpSw6428C");

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("...."+result);
            JSONObject jsonObject = new JSONObject(result);
            JSONObject json= (JSONObject)jsonObject.getJSONArray("result").get(0); 
            return  (json.get("name")==null)?"0":json.get("name")+"";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

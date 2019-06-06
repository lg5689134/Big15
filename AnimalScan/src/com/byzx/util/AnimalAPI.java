/**
 * @filename AnimalAPI.java
 * @author lg
 * @date 2019年4月2日 下午4:13:21
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import org.json.JSONObject;
import com.baidu.aip.imageclassify.AipImageClassify;

public class AnimalAPI {
	/*使用SDK方式调用百度云接口*/
	private static AipImageClassify client = new AipImageClassify("15871052", "tqDlru9AERM5USZGQTkle6Xu", "Wp51Aa8Fl6TbgXNmdNtZAk8EpSw6428C");

	 /**
     * sdk方式
     * @comment 
     * @throws IOException
     * @version 1.0
     */
	public static String animalSDK(String image) throws IOException {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("baike_num", "0");//返回百科结果
	    // 参数为本地路径
	   //  String image = "D:/animal/6.jpg";
	    JSONObject res = client.advancedGeneral(image, options);
	    System.out.println(res.toString(2));
	    return res.toString(2);
	 }
	
	/**
	 * @comment rest-api方式,相对比较准确
	 * @return
	 * @version 1.0
	 */
	public static String animalAPI(String image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
        try {
            // 本地文件路径
           // String image = "D:/animal/2.jpg";
        	  byte[] imgData = FileUtil.readFileByBytes(image);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam + "&top_num=" + 6+"&baike_num=0";

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.d38e14377e1f54afe309b29c14467ed0.2592000.1559180044.282335-15826305";
           //AuthService.getAuth("tqDlru9AERM5USZGQTkle6Xu", "Wp51Aa8Fl6TbgXNmdNtZAk8EpSw6428C");
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject json=new JSONObject(result);
            JSONObject obj=(JSONObject)json.getJSONArray("result").get(0);
            System.out.println(result);
            System.out.println(obj.get("name"));
            return (obj.get("name")==null)?"0":(obj.get("name")+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}

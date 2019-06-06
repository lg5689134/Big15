package com.byzx.face;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.byzx.util.Base64Util;
import com.byzx.util.FileUtil;
import com.byzx.util.GsonUtils;
import com.byzx.util.HttpUtil;

/*
 * 登录请求处理类
 */
@WebServlet("/faceLogin")//指定前端页面要访问的路径
public class LoginController extends HttpServlet{
	private static final long serialVersionUID = 6135120049380996083L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean flag = false;
		//接收参数
		String image1 = req.getParameter("base64");
		System.out.println(image1);
		//取出本地数据，查询数据库获取，数据库中保存图片的base64格式
//		String base64ById = DbUtil.getBase64ById(sql, 1);
		 try {
            byte[] bytes2 = FileUtil.readFileByBytes("D:/IMG_20180816_102245.jpg");
            String image2 = Base64Util.encode(bytes2);
            List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);
            //封装json数组方式，进行参数对比
           //说明：两张图片的上传使用json格式，[{"image":""..},{"image":""..}]具体参考api
            String param = GsonUtils.toJson(images);
            System.out.println("param:"+param);
           // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
           // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.68a0c09ebd959943fd28127a1697a7f8.2592000.1558784201.282335-15826305";
            String result = HttpUtil.post(url, accessToken, "application/json",param);
            JSONObject jsonObject = JSONObject.fromObject(result);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			if(jsonObject2!=null && jsonObject2.containsKey("score")){
				double double1 = jsonObject2.getDouble("score");//人脸相似度得分，推荐阈值80分
//				System.out.println(double1);
//				System.out.println(result);
				if(double1>80){
					flag=true;
				}	
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(flag){
				resp.getWriter().print("true");
			}else{
				resp.getWriter().print("false");
			}
		}		
	}
	
	
	
	
}

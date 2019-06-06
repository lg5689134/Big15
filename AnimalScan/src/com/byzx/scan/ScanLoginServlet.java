/**
 * @filename ScanLoginServlet.java
 * @author lg
 * @date 2019年3月25日 上午10:57:43
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.scan;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * 扫描二维码实现自动登录，建立长链接
 * */
@WebServlet("/scanLogin")
public class ScanLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -8775969262466631677L;

	public ScanLoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Writer out=response.getWriter();
		try {
			String token=request.getParameter("token");
			String str="";
			boolean t=false;//记录token是否存在
			
			//采用长链接方式验证登录状态
			boolean flag=true;
			long start=System.currentTimeMillis();
			while(flag){
			 if(token!=null && !"".equals(token)){
				Thread.sleep(2000);//当前线程睡眠2秒
				//1、判断token是否是系统分配的token,确保数据安全性
				Set<String> set=LoginUser.tokens;
				for(String s:set){
					if(s.equals(token)){
						t=true;
						break;
					}
				}
				//2、判断token和用户是否绑定
				String userName=LoginUser.users.get(token);
				//3、如果用户token已经绑定，自动登录跳转，删除token和绑定关系
				if(t && userName!=null && !"".equals(userName)){
					//登录成功,删除内存中已经扫描过的token及绑定的用户信息
					LoginUser.tokens.remove(token);
					LoginUser.users.remove(token);
					str="{\"userName\":\""+userName+"\"}";
					flag=false;//登录成功后退出循环
				}else{//用户或token不存在
					str="{\"userName\":\"\"}";
				}
			 }else{//token为空
					str="{\"userName\":\"\"}";
			 }
			 long end=System.currentTimeMillis();
			 if(end-start>5000){//超过5秒后，超时退出循环
				 flag=false;
			 }
			}
			
			//普通方法
			/*if(token!=null && !"".equals(token)){
				//1、判断token是否是系统分配的token,确保数据安全性
				Set<String> set=LoginUser.tokens;
				for(String s:set){
					if(s.equals(token)){
						t=true;
						break;
					}
				}
				//2、判断token和用户是否绑定
				String userName=LoginUser.users.get(token);
				//3、如果用户token已经绑定，自动登录跳转，删除token和绑定关系
				if(t && userName!=null && !"".equals(userName)){
					//登录成功,删除内存中已经扫描过的token及绑定的用户信息
					LoginUser.tokens.remove(token);
					LoginUser.users.remove(token);
					str="{\"userName\":\""+userName+"\"}";
				}else{//用户或token不存在
					str="{\"userName\":\"\"}";
				}
			}else{//token为空
				str="{\"userName\":\"\"}";
			}*/
			
			out.write(str);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	public void init() throws ServletException {
	
	}

}

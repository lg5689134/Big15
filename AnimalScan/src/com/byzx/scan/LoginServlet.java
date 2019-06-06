/**
 * @filename LoginServlet.java
 * @author lg
 * @date 2019年3月25日 上午10:47:32
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
 * 登录验证,建立token和用户的绑定关系
 * */
@WebServlet("/tologin")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -7073150050399128167L;
	
	public LoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");//汉字乱码解决方法
		response.setContentType("text/html;charset=utf-8");
		Writer out=response.getWriter();
		try {
			String userName=request.getParameter("userName");
			String token=request.getParameter("token");
			String str="";
			boolean t=false;//记录token是否存在
			boolean u=false;//记录系统中是否包含该用户
			if(userName!=null && token!=null && !"".equals(userName) && !"".equals(token)){
				//1、判断token是否是系统分配的token,确保数据安全性
				Set<String> set=LoginUser.tokens;
				for(String s:set){
					if(s.equals(token)){
						t=true;
						break;
					}
				}
				//2、判断用户名是否正确
				//可以通过自己业务，如可以查询系统用户表中是否包含该用户
				//如果包含用户信息
					u=true;
				//3、如果12验证通过后，绑定token和用户的关系
				if(t && u){
					LoginUser.users.put(token, userName);
					str="{\"userName\":\""+userName+"\"}";
				}else{//用户或token不存在
					str="{\"userName\":\"\"}";
				}
			}else{//用户名称或token为空
				str="{\"userName\":\"\"}";
			}
			out.write(str);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}

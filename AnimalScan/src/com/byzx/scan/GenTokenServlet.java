/**
 * @filename GenToken.java
 * @author lg
 * @date 2019年3月25日 上午10:08:26
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.scan;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 *生成验token唯一标识 
 * */
@WebServlet("/genToken")
public class GenTokenServlet extends HttpServlet {
	private static final long serialVersionUID = -5513525938324942526L;

	 public GenTokenServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			//生成随机的uuid作为唯一标识
			String token=(UUID.randomUUID()+"").replace("-", "");
			//存放到线程安全的set中
			LoginUser.tokens.add(token);
			out.print(token);
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

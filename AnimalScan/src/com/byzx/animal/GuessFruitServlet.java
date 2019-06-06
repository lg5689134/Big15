/**
 * @filename GuessFruitServlet.java
 * @author lg
 * @date 2019年4月2日 上午9:54:16
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.animal;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.byzx.util.FruitAPI;
/**
 * 智能识别果蔬名称
 */
@WebServlet("/guessFruit")
public class GuessFruitServlet extends HttpServlet{
	private static final long serialVersionUID = -8206196782513151159L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fullPath="";
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
		Writer out=response.getWriter();
		try {
			String imgPath=request.getParameter("imgPath");
			if(imgPath!=null && imgPath.length()>1){
				fullPath=request.getSession().getServletContext().getRealPath("/")+imgPath;
				//System.out.println(fullPath);
				String res=FruitAPI.fruitAPI(fullPath);
				System.out.println(res);
				out.write(res);
		}else{
			//无法获取图片
			out.write("-1");
		}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}

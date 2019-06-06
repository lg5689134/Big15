/**
 * @filename GuessAnimal.java
 * @author lg
 * @date 2019年4月1日 下午4:30:35
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

import com.byzx.util.AnimalAPI;
/**
 * 智能识别动物名称
 */
@WebServlet("/guessAnimal")
public class GuessAnimalServlet extends HttpServlet{
	private static final long serialVersionUID = 6494828163039739727L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String fullPath="";		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
		Writer out=response.getWriter();
		try {
			String imgPath=request.getParameter("imgPath");
			if(imgPath!=null && imgPath.length()>1){
				fullPath=request.getSession().getServletContext().getRealPath("/")+imgPath;
				System.out.println(fullPath);
				String res=AnimalAPI.animalAPI(fullPath);
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

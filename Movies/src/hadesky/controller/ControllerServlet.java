package hadesky.controller;

import hadesky.domain.Category;
import hadesky.service.Business;
import hadesky.service.impl.BusinessImpl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utli.FillBeanUtil;

public class ControllerServlet extends HttpServlet {
	private Business s = new BusinessImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String opString = request.getParameter("op");
		if ("addCategory".equals(opString)) {
			addCategory(request,response);
		}
		
		
	}

	private void addCategory(HttpServletRequest request, HttpServletResponse response) {
		Category category = FillBeanUtil.fillBean(request, Category.class);
		try {
			s.addCategory(category);
			request.setAttribute("msg", "success");
			request.getRequestDispatcher("/Admin/message.jsp").forward(request, response);
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

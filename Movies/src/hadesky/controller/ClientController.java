package hadesky.controller;

import hadesky.commons.Page;
import hadesky.domain.Category;
import hadesky.domain.Users;
import hadesky.service.Business;
import hadesky.service.impl.BusinessImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.security.jgss.LoginConfigImpl;
import utli.FillBeanUtil;

public class ClientController extends HttpServlet {

	private Business s = new BusinessImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opString = request.getParameter("op");
		if ("regist".equals(opString)) {
			regist(request,response);
		}else if("login".equals(opString)){
			Login(request,response);
		}else if ("logout".equals(opString)) {
			logout(request,response);
		}else if("showIndex".equals(opString)){
			showIndex(request,response);
		}else if("showCategoryBooks".equals(opString)){
			showCategoryBooks(request,response);
		}
	}
	//分类查询
	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("pageNum");
		String categoryId = request.getParameter("categoryId");
		//分类
		List<Category> cs = s.findAllCategories();
		//分页
		Page page = s.findAllBookPageRecords(num,categoryId);
		page.setUrl("/User/ClientController?op=showCategoryBooks&categoryId="+categoryId);
		
		request.setAttribute("cs", cs);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/User/listAllBook.jsp").forward(request, response);
		
	}
	//首页
	private void showIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("pageNum");
		//分类
		List<Category> cs = s.findAllCategories();
		//分页
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/User/ClientController?op=showIndex");
		
		request.setAttribute("cs", cs);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/User/listAllBook.jsp").forward(request, response);
		
	}
	//退出登录
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	//用户登录
	private void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Users users = s.Login(username, password);
		
		if (users != null) {
			request.getSession().setAttribute("user", users);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "登录失败");
			request.getRequestDispatcher("/User/message.jsp").forward(request, response);
		}
	}

	//用户注册
	private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Users users = FillBeanUtil.fillBean(request, Users.class);
		users.setCode(UUID.randomUUID().toString());
		s.regist(users);
		
		request.setAttribute("msg", "success");
		request.getRequestDispatcher("/User/message.jsp").forward(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}

}

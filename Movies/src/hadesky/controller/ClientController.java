package hadesky.controller;

import hadesky.bean.Cart;
import hadesky.bean.CartItem;
import hadesky.commons.Page;
import hadesky.domain.*;
import hadesky.service.Business;
import hadesky.service.impl.BusinessImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.security.jgss.LoginConfigImpl;
import utli.FillBeanUtil;
import utli.IdGenertor;
import utli.PaymentUtil;
import utli.SendMail;

public class ClientController extends HttpServlet {

	private Business s = new BusinessImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opString = request.getParameter("op");
		if ("regist".equals(opString)) {
			regist(request, response);
		} else if ("login".equals(opString)) {
			Login(request, response);
		} else if ("logout".equals(opString)) {
			logout(request, response);
		}  else if ("showIndex".equals(opString)) {
			showIndex(request, response);
		} else if ("showCategoryBooks".equals(opString)) {
			showCategoryBooks(request, response);
		} else if ("showBookDetails".equals(opString)) {
			showBookDetails(request, response);
		} else if ("buyBook".equals(opString)) {
			buyBook(request, response);
		} else if ("changeNum".equals(opString)) {
			changeNum(request, response);
		} else if ("delOneItem".equals(opString)) {
			delOneItem(request, response);
		}  else if ("genOrders".equals(opString)) {
			genOrders(request, response);
		}else if("active".equals(opString)){
			active(request,response);
		}
	}

	private void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		if(code!=null&&!"".equals(code)){
			s.activeCustomer(code);
			response.getWriter().write("注册成功，2秒后跳转页面");
			response.setHeader("Refresh", "2;URL="+request.getContextPath());
		}else{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	private void pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum");// ������
		String money = request.getParameter("money");// ֧�����
		String pd_FrpId = request.getParameter("pd_FrpId");// ����

		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";
		String p2_Order = orderNum;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "unknown";
		String p6_Pcat = "unknown";
		String p7_Pdesc = "unknown";
		String p8_Url = "http://localhost:8080/Movies/User/PaymentResponse";
		String p9_SAF = "1";
		String pa_MP = "no";
		String pr_NeedResponse = "1";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse,
				"69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");

		request.setAttribute("p0_Cmd", p0_Cmd);
		request.setAttribute("p1_MerId", p1_MerId);
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur", p4_Cur);
		request.setAttribute("p5_Pid", p5_Pid);
		request.setAttribute("p6_Pcat", p6_Pcat);
		request.setAttribute("p7_Pdesc", p7_Pdesc);
		request.setAttribute("p8_Url", p8_Url);
		request.setAttribute("p9_SAF", p9_SAF);
		request.setAttribute("pa_MP", pa_MP);
		request.setAttribute("pr_NeedResponse", pr_NeedResponse);
		request.setAttribute("pd_FrpId", pd_FrpId);
		request.setAttribute("hmac", hmac);

		request.getRequestDispatcher("/sure.jsp").forward(request, response);

	}

	private void genOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// �ж��û���û�е�¼
		Users c = (Users) session.getAttribute("user");
		if (c == null) {
			response.sendRedirect(request.getContextPath() + "/User/login.jsp");
			return;
		}
		// �ѹ��ﳵ---��������
		Cart cart = (Cart) session.getAttribute("cart");

		Order order = new Order();
		String orderNum = IdGenertor.genOrdersNum();
		order.setOrderNum(orderNum);
		order.setQuantity(cart.getTotalQuantity());
		order.setAmount(cart.getAmount());
		order.setCustomer(c);

		// ������---->��������
		Map<String, CartItem> items = cart.getItems();
		for (Map.Entry<String, CartItem> me : items.entrySet()) {
			OrderItem oi = new OrderItem();
			oi.setId(UUID.randomUUID().toString());
			oi.setQuantity(me.getValue().getQuantity());
			oi.setPrice(me.getValue().getTotalPrice());
			oi.setBook(me.getValue().getBook());
			order.getItems().add(oi);
		}
		// ������ϵ

		// ����
		s.genOrder(order);// ��ɶ���
		// ת��֧��ҳ��
		request.getRequestDispatcher(
				"/User/pay.jsp?orderNum=" + orderNum + "&amount="
						+ order.getAmount()).forward(request, response);

	}

	// 订单
	private void showCustomerOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 获得登录的用户信息
		Users c = (Users) session.getAttribute("user");
		if (c == null) {
			response.sendRedirect(request.getContextPath() + "/User/login.jsp");
			return;
		}
		List<Order> orders = s.findOrdersByCustomer(c);
		request.setAttribute("os", orders);
		request.getRequestDispatcher("/User/listOrders.jsp").forward(request,
				response);
	}

	// 删除购物项
	private void delOneItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String bookId = request.getParameter("bookId");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.getItems().remove(bookId);
		response.sendRedirect(request.getContextPath() + "/User/showCart.jsp");
	}

	// 购物项数量
	private void changeNum(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String bookId = request.getParameter("bookId");
		String newNum = request.getParameter("num");

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		CartItem item = cart.getItems().get(bookId);
		item.setQuantity(Integer.parseInt(newNum));
		request.getRequestDispatcher("/User/showCart.jsp").forward(request,
				response);
	}

	// 加入购入车
	private void buyBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		// 购物车ﳵ
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		// 添加到购物车ﳵ
		cart.addBook(book);

		request.setAttribute("msg", "添加成功，<a href='" + request.getContextPath()
				+ "'>点击返回</a>");
		request.getRequestDispatcher("/User/message.jsp").forward(request,
				response);
	}

	// 详情页
	private void showBookDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");

		Book book = s.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/User/showDetails.jsp").forward(request,
				response);
	}

	// 分类查询
	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("pageNum");
		String categoryId = request.getParameter("categoryId");
		// 分类
		List<Category> cs = s.findAllCategories();
		// 分页
		Page page = s.findAllBookPageRecords(num, categoryId);
		page.setUrl("/User/ClientController?op=showCategoryBooks&categoryId="
				+ categoryId);

		request.setAttribute("cs", cs);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/User/listAllBook.jsp").forward(request,
				response);

	}

	// 首页
	private void showIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("pageNum");
		// 分类
		List<Category> cs = s.findAllCategories();
		// 分页
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/User/ClientController?op=showIndex");

		request.setAttribute("cs", cs);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/User/listAllBook.jsp").forward(request,
				response);

	}

	// 退出登录
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	// 用户登录
	private void Login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Users users = s.Login(username, password);

		if (users != null) {
			request.getSession().setAttribute("user", users);
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		} else {
			request.setAttribute("msg", "登录失败");
			request.getRequestDispatcher("/User/message.jsp").forward(request,
					response);
		}
	}

	// 用户注册
	private void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Users users = FillBeanUtil.fillBean(request, Users.class);
		users.setCode(UUID.randomUUID().toString());
		s.regist(users);

		SendMail sm = new SendMail(users);
		sm.start();
		
		request.setAttribute("msg", "请用邮箱验证");
		request.getRequestDispatcher("/User/message.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

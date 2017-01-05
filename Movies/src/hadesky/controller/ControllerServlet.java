package hadesky.controller;

import hadesky.commons.Page;
import hadesky.domain.Book;
import hadesky.domain.Category;
import hadesky.service.Business;
import hadesky.service.impl.BusinessImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

import utli.FillBeanUtil;

public class ControllerServlet extends HttpServlet {
	private Business s = new BusinessImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String opString = request.getParameter("op");
		if ("addCategory".equals(opString)) {
			addCategory(request, response);
		} else if ("showAllCategories".equals(opString)) {
			showAllCategories(request, response);
		} else if ("showAddBookUI".equals(opString)) {
			showAddBookUI(request, response);
		} else if ("addBook".equals(opString)) {
			try {
				addBook(request, response);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else if("showAllBooks".equals(opString)){
			showAllBooks(request,response);
		}

	}
	//查询图书
	private void showAllBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("pageNum");
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/Admin/ControllerServlet?op=showAllBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/Admin/listBooks.jsp").forward(request, response);
	}

	// 添加图书
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean isMultpart = ServletFileUpload.isMultipartContent(request);
		if (!isMultpart) {
			request.setAttribute("msg", "保存失败");
			request.getRequestDispatcher("/Admin/message.jsp").forward(request,
					response);
			return;
		}

		ServletFileUpload fileUpload = new ServletFileUpload(
				new DiskFileItemFactory());
		List<FileItem> items = fileUpload.parseRequest(request);

		Book book = new Book();
		for (FileItem item : items) {
			if (item.isFormField()) {
				String nameString = item.getFieldName();
				String vauleString = item.getString(request
						.getCharacterEncoding());
				BeanUtils.setProperty(book, nameString, vauleString);
			} else {
				// 文件上传
				String fileName = item.getName();
				if (fileName != null && !fileName.trim().equals("")) {
					// 文件名
					fileName = UUID.randomUUID().toString() + "."
							+ FilenameUtils.getExtension(fileName);
					// 文件路径
					String storeDirectory = getServletContext().getRealPath(
							"/images");
					String path = makeDirs(storeDirectory, fileName);// /dir1/dir2

					book.setPath(path);
					book.setPhotoFileName(fileName);

					// 写入文件
					item.write(new File(storeDirectory + path + "/" + fileName));
				}
			}
		}
		// 保存
		s.addBook(book);
		request.setAttribute("msg", "添加成功");
		request.getRequestDispatcher("/Admin/message.jsp").forward(request,
				response);
	}

	// 创建文件保存路径
	private String makeDirs(String storeDirectory, String fileName) {
		int hashcode = fileName.hashCode();
		int dir1 = hashcode & 0xf;
		int dir2 = (hashcode & 0xf0) >> 4;

		String newPath = "/" + dir1 + "/" + dir2;
		File file = new File(storeDirectory, newPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return newPath;
	}

	// 添加图书，先查出分类信息
	private void showAddBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> list = s.findAllCategories();
		request.setAttribute("c", list);
		request.getRequestDispatcher("/Admin/addBooks.jsp").forward(request,
				response);
	}

	// 查询分类
	private void showAllCategories(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Category> list = s.findAllCategories();
		request.setAttribute("sc", list);
		request.getRequestDispatcher("/Admin/listCategory.jsp").forward(
				request, response);

	}

	// 添加分类
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Category category = FillBeanUtil.fillBean(request, Category.class);
		s.addCategory(category);
		request.setAttribute("msg", "success");
		request.getRequestDispatcher("/Admin/message.jsp").forward(request,
				response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

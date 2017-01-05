package hadesky.service.impl;

import java.util.List;
import java.util.UUID;

import hadesky.commons.Page;
import hadesky.dao.BookDao;
import hadesky.dao.CategoryDao;
import hadesky.dao.UserDao;
import hadesky.dao.impl.BookDaoImpl;
import hadesky.dao.impl.CategoryDaoImpl;
import hadesky.dao.impl.UserDaoImpl;
import hadesky.domain.Book;
import hadesky.domain.Category;
import hadesky.domain.Users;
import hadesky.service.Business;

public class BusinessImpl implements Business{
	private UserDao userDao = new UserDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	private CategoryDao categoryDao = new CategoryDaoImpl();
	public void addCategory(Category category) { 
		category.setId(UUID.randomUUID().toString());
		categoryDao.addCategory(category);
	}

	public Category findOneCategory(String id) {
		return categoryDao.findOneCategory(id);
	}

	public List<Category> findAllCategories() {
		return categoryDao.findAllCategoryes();
	}

	public void addBook(Book book) {
		book.setId(UUID.randomUUID().toString());
		bookDao.addBook(book);
	}

	public Book findOneBook(String bookname) {
		return bookDao.findOneBook(bookname);
	}

	public List<Book> findAllBooks(int startindex,int offset) {
		return bookDao.findAllBooks(startindex,offset);
	}

	public Page findAllBookPageRecords(String pagenum) {
		
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.findAllBooksNumber();
		Page page = new Page(currentPageNum, totalRecords);
		
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

	public void regist(Users users) {
		users.setId(UUID.randomUUID().toString());
		userDao.addUser(users);
	}

	public Users Login(String username,String password){
		return userDao.findOneUser(username, password);
	}

	public Page findAllBookPageRecords(String pagenum, String categoryId) {
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.findAllBooksNumber(categoryId);
		Page page = new Page(currentPageNum, totalRecords);
		
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize(),categoryId));
		
		return page;
	}


	
}

package hadesky.service.impl;

import java.util.List;
import java.util.UUID;

import hadesky.dao.BookDao;
import hadesky.dao.CategoryDao;
import hadesky.dao.impl.BookDaoImpl;
import hadesky.dao.impl.CategoryDaoImpl;
import hadesky.domain.Book;
import hadesky.domain.Category;
import hadesky.service.Business;

public class BusinessImpl implements Business{

	private BookDao bookDao = new BookDaoImpl();
	private CategoryDao categoryDao = new CategoryDaoImpl();
	public void addCategory(Category category) { 
		category.setId(UUID.randomUUID().toString());
		categoryDao.addCategory(category);
	}

	public Category findOneCategory(int id) {
		return categoryDao.findOneCategory(id);
	}

	public List<Category> findAllCategories(int startindex,int offset) {
		return categoryDao.findAllCategoryes(startindex,offset);
	}

	public void addBook(Book book) {
		bookDao.addBook(book);
	}

	public Book findOneBook(String bookname) {
		return bookDao.findOneBook(bookname);
	}

	public List<Book> findAllBooks(int startindex,int offset) {
		return bookDao.findAllBooks(startindex,offset);
	}

}

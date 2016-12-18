package hadesky.service.impl;

import java.util.List;
import hadesky.dao.BookDao;
import hadesky.dao.CategoryDao;
import hadesky.domain.Book;
import hadesky.domain.Category;
import hadesky.service.Business;

public class BusinessImpl implements Business{

	private BookDao bookDao = new BookDaoImpl();
	private CategoryDao categoryDao = new CategoryDaoImpl();
	public void addCategory(Category category) { 
		categoryDao.addCategory(category);
	}

	public Category findOneCategory(String bookname) {
		return categoryDao.findOneCategory(bookname);
	}

	public List<Category> findAllCategories() {
		return categoryDao.findAllCategoryes();
	}

	public void addBook(Book book) {
		bookDao.addBook(book);
	}

	public Book findOneBook(String bookname) {
		return bookDao.findOneBook(bookname);
	}

	public List<Book> findAllBooks() {
		return bookDao.findAllBooks();
	}

}

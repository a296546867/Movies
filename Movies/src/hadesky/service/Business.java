package hadesky.service;

import java.util.List;

import hadesky.domain.Book;
import hadesky.domain.Category;

public interface  Business {

	void addCategory(Category category);
	
	Category findOneCategory(int id);
	
	List<Category> findAllCategories();
	
	
	void addBook(Book book);
	
	Book findOneBook(String Bookname);
	
	List<Book> findAllBooks(int startindex,int offset);
}

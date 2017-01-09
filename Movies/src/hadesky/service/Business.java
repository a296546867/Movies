package hadesky.service;

import java.util.List;

import hadesky.commons.Page;
import hadesky.domain.Book;
import hadesky.domain.Category;
import hadesky.domain.Order;
import hadesky.domain.Users;

public interface  Business {

	void addCategory(Category category);
	
	Category findOneCategory(String id);
	
	List<Category> findAllCategories();
	
	
	void addBook(Book book);
	
	Book findOneBook(String Bookname);
	
	List<Book> findAllBooks(int startindex,int offset);

	Page findAllBookPageRecords(String num);
	

	
	void regist(Users users);
	Users Login(String username,String password);

	Page findAllBookPageRecords(String num, String categoryId);

	Book findBookById(String bookId);

	List<Order> findOrdersByCustomer(Users c);

	void genOrder(Order order);

	void activeCustomer(String code);
	
}

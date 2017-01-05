package hadesky.dao;

import java.util.List;

import hadesky.domain.Book;

public interface BookDao {

	void addBook(Book book);

	Book findOneBook(String id);

	List<Book> findAllBooks(int startindex,int offset);

	int findAllBooksNumber();

	List findPageBooks(int startIndex, int pageSize);

	int findAllBooksNumber(String categoryId);

	List findPageBooks(int startIndex, int pageSize, String categoryId);

}

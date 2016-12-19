package hadesky.dao;

import java.util.List;

import hadesky.domain.Book;

public interface BookDao {

	void addBook(Book book);

	Book findOneBook(String bookname);

	List<Book> findAllBooks(int startindex,int offset);

}

package hadesky.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utli.DBCPUtil;

import hadesky.dao.BookDao;
import hadesky.domain.Book;
import hadesky.exception.DaoException;

public class BookDaoImpl implements BookDao {

	private QueryRunner qRunner = new QueryRunner(DBCPUtil.getDataSource());
	public void addBook(Book book) {
		try {
			qRunner.update("insert into books (id,name,author,price,path,photoFileName,description,categoryid) values(?,?,?,?,?,?,?,?)",
					book.getId(),
					book.getName(),
					book.getAuthor(),
					book.getPrice(),
					book.getPath(),
					book.getPhotoFileName(),
					book.getDescription(),
					book.getCategoryid());
			
		} catch (Exception e) {
			throw new  DaoException(e);
		}
	}

	public Book findOneBook(String bookname) {
		try {
			return qRunner.query("select * from books where name=?", new BeanHandler<Book>(Book.class),bookname);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Book> findAllBooks(int startindex,int offset) {
		try {
			return qRunner.query("select * from books limit ?,?", new BeanListHandler<Book>(Book.class),startindex,offset);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}

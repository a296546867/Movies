package hadesky.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utli.DBCPUtil;

import hadesky.dao.CategoryDao;
import hadesky.domain.Category;
import hadesky.exception.DaoException;

public class CategoryDaoImpl implements CategoryDao {

	private QueryRunner queryRunner = new QueryRunner(DBCPUtil.getDataSource());
	public void addCategory(Category category) {
		try {
			queryRunner.update("insert into categorys (id,name,description) values(?,?,?)",
					category.getId(),
					category.getName(),
					category.getDescription());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Category findOneCategory(int id) {
		try {
			return queryRunner.query("select * from categorys where id = ?", new BeanHandler<Category>(Category.class),id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Category> findAllCategoryes(int startindex,int offset) {
		try {
			return queryRunner.query("select * from categorys limit ?,?", new BeanListHandler<Category>(Category.class),startindex,offset);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}

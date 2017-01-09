package hadesky.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import utli.DBCPUtil;

import com.sun.org.apache.bcel.internal.generic.NEW;

import hadesky.dao.UserDao;
import hadesky.domain.Users;
import hadesky.exception.DaoException;

public class UserDaoImpl implements UserDao {

	private QueryRunner qRunner = new QueryRunner(DBCPUtil.getDataSource());

	public void addUser(Users user) {
		try {
			qRunner.update(
					"insert into customers (id,username,password,phone,address,email,actived,code)values(?,?,?,?,?,?,?,?)",
					user.getId(), user.getUsername(), user.getPassword(),
					user.getPhone(), user.getAddress(), user.getEmail(),
					user.isActived(), user.getCode());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Users findOneUser(String username, String password) {
		try {
			return qRunner.query(
					"select * from customers where username=? and password=?",
					new BeanHandler<Users>(Users.class), username, password);

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Users findbycode(String code) {
		try {
			return qRunner.query("select * from customers where code=?",
					new BeanHandler<Users>(Users.class), code);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Users findByCode(String code) {
		try {
			return qRunner.query("select * from customers where code=?", new BeanHandler<Users>(Users.class),code);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void update(Users c) {
		try {
			qRunner.update("update customers set username=?,password=?,phone=?,address=?,email=?,actived=?,code=? where id=?", 
					c.getUsername(),
					c.getPassword(),
					c.getPhone(),
					c.getAddress(),
					c.getEmail(),
					c.isActived(),
					c.getCode(),
					c.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

}

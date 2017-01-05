package hadesky.dao;

import hadesky.domain.Users;

import javax.xml.registry.infomodel.User;

public interface UserDao {

	void addUser(Users user);
	Users findOneUser(String username,String password);
	Users findbycode(String code);
}

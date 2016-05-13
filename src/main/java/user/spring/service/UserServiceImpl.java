package user.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import user.spring.dao.UserDao;
import user.spring.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User getUser(String login) {
		return userDao.getUser(login);
	}
}

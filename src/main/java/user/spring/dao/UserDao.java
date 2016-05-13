package user.spring.dao;

import user.spring.model.User;

public interface UserDao {
	public User getUser(String login);
}
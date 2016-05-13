package user.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import user.spring.dao.RoleDao;
import user.spring.model.Role;

public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public Role getRole(int id) {
		return roleDao.getRole(id);
	}
}

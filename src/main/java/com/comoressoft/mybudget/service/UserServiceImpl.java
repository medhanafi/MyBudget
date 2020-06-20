package com.comoressoft.mybudget.service;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.comoressoft.mybudget.dto.UserDTO;
import com.comoressoft.mybudget.entity.User;
import com.comoressoft.mybudget.mapper.GlobalMapper;
import com.comoressoft.mybudget.util.CollectionsUtils;

@Service
public class UserServiceImpl extends UserDetailsServiceImpl implements UserService {

	@Autowired
	GlobalMapper mapper;

	@Override
	public List<UserDTO> findAll() throws Exception {
		List<User> users = this.getRepository().findAll();
		
		if (users != null && !users.isEmpty()) {
			return CollectionsUtils.apply(users,
					user -> this.mapper.userToUserDTO(user));
		} else {
			throw new Exception("The users collections is empty");
		}
	}

	@Override
	public UserDTO save(User user) {
		user.setPassword(new BCryptPasswordEncoder(8).encode(user.getPassword()));
		return this.mapper.userToUserDTO(this.getRepository().save(user));
	}

	@Override
	public UserDTO findOne(long id) throws Exception {
		Optional<User> uData = this.getRepository().findById(id);
		if (uData.isPresent()) {
			return this.mapper.userToUserDTO(this.getRepository().findById(id).get());
		} else {
			throw new Exception("User not found");
		}
	}
	

	@Override
	public UserDTO findOne(String name) throws Exception {
		User uData = this.getRepository().findByUsername(name);
		if (uData!=null) {
			return this.mapper.userToUserDTO(uData);
		} else {
			throw new Exception("User not found");
		}
	}

	@Override
	public void delete(Long id) {
		this.getRepository().deleteById(id);
	}

	@Override
	public User findByUsername(String name) {
		return this.getRepository().findByUsername(name);
	}

}

package com.comoressoft.mybudget.service;

import java.util.List;

import com.comoressoft.mybudget.dto.UserDTO;
import com.comoressoft.mybudget.entity.User;


public interface UserService {

    List<UserDTO> findAll() throws Exception;

    UserDTO save(User user);

    UserDTO findOne(long id) throws Exception;

    void delete(Long id);

	User findByUsername(String name);

}
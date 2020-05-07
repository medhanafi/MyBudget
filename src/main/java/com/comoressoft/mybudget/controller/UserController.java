package com.comoressoft.mybudget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comoressoft.mybudget.dto.UserDTO;
import com.comoressoft.mybudget.entity.User;
import com.comoressoft.mybudget.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", ""})
    public List<UserDTO> listUser() throws Exception {
        return userService.findAll();
    }

    @PostMapping(value = "/user")
    public UserDTO create(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(value = "/user/{id}")
    public UserDTO findOne(@PathVariable long id) throws Exception {
        return userService.findOne(id);
    }

    @PutMapping(value = "/user/{id}")
    public UserDTO update(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return userService.save(user);
    }

    @DeleteMapping(value = "/user/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        userService.delete(id);
    }

}

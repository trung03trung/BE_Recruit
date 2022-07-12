package com.itsol.recruit.service;

import com.itsol.recruit.entity.User;
import com.itsol.recruit.web.vm.SeachVM;

import java.util.List;

public interface UserService {

    public List<User> getAllUser();
    public User findById(Long id);
    public User findUserByUserName(String userName);
    public Object updateUser(User user);
    public List<User> seachUser(SeachVM seachVM);

}

package com.itsol.recruit.service;

import com.itsol.recruit.entity.User;
import com.itsol.recruit.web.vm.UserVM;

import java.util.List;

public interface UserService {

    public List<User> getAllUser();
    public List<User> getAllUserJe();
    public User findById(Long id);
    public User findUserByUserName(String userName);
    public Object updateUser(User user);
    public UserVM getAllJE(int pageNo, int pageSize, String sortBy, String sortDir);
}

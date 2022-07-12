package com.itsol.recruit.dao;

import com.itsol.recruit.entity.User;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDao {
    List<User> getAllUserJe();
    List<User> getAllUser();
}

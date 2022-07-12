package com.itsol.recruit.dao;

import com.itsol.recruit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<User> getAllUserJe() {
        // chuyển đổi một hàng trong bảng,ánh xạ lớp chỉ định
        List<User> users = jdbcTemplate.query
                ("select u.id,u.name,u.USER_NAME,u.PASSWORD,u.EMAIL,u.PHONE_NUMBER, u.ACTIVATE from USERS u join PERMISSTION p on u.id = p.user_id where p.role_id = 2", new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }
}

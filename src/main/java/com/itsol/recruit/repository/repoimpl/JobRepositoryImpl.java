package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.BaseRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JobRepositoryImpl extends BaseRepository {

    public List<User> getAllByRole(List<Role> role){
        String sql="SELECT * from users u join permisstion p on u.id=p.user_id " +
                "WHERE p.role_id=?";
        List<User> users=getJdbcTemplate().query(sql,new Object[]{role.get(0).getId()},new BeanPropertyRowMapper<>(User.class));
        return users;
    }


}

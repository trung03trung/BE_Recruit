package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.BaseRepository;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import com.itsol.recruit.web.vm.SeachVM;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryExt {
    @Override
    public User getAllUser() {
        return null;
    }

    public List<User> seachUser(SeachVM seachVM){
        String query = "SELECT * FROM\n" +
                "(\n" +
                "    SELECT a.*, rownum r__\n" +
                "    FROM\n" +
                "    (\n" +
                "        SELECT USERS.ID, USERS.NAME, USERS.USER_NAME, USERS.EMAIL, USERS.HOME_TOWN, USERS.GENDER, USERS.BIRTH_DAY, USERS.ACTIVATE, USERS.IS_DELETE, USERS.PHONE_NUMBER,USERS.AVATAR FROM USERS JOIN PERMISSTION ON USERS.ID = PERMISSTION.USER_ID\n" +
                "                  JOIN ROLES ON ROLES.ID = PERMISSTION.ROLE_ID WHERE ROLES.ID = 2 and name LIKE '%"+seachVM.getName()+"%' and user_name LIKE '%"+seachVM.getUserName()+"%' and email LIKE '%"+seachVM.getEmail()+"%'\n" +
                "        ORDER BY id DESC\n" +
                "    ) a\n" +
                "    WHERE rownum < (("+seachVM.getPageNumber()+" * "+seachVM.getPageSize()+") + 1 )\n" +
                ")\n" +
                "WHERE r__ >= ((("+seachVM.getPageNumber()+"-1) * "+seachVM.getPageSize()+") + 1)";
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(User.class));
    }
}

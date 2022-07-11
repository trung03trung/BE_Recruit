package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExt {

    User findByUserName(String userName);

    User findByEmail(String email);

    User findUserByEmail(String email);

    User findUserByPhoneNumber(String number);

    User findUserById(Long id);

}

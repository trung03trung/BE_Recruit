package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByCode(String code);

    List<Role> findByCode(String code);

}

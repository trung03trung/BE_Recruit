package com.itsol.recruit.service.impl;

import com.itsol.recruit.dao.UserDaoImpl;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.web.vm.UserVM;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Getter
@Service
@Transactional
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserDaoImpl userDao;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserDaoImpl userDao) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public List<User> getAllUserJe() {
//        for (int i = 0; i <userDao.getAllUserJe().size(); i++) {
//            User user = userDao.getAllUserJe().get(i);
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            encoder.matches(password, user.getPassword());
//            user.setPassword(enCryptPassword);
//        }
        return userDao.getAllUserJe();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Object updateUser(User user) {
        User userChange = userRepository.findById(user.getId()).orElse(null);
        if(userChange == null){
            userRepository.save(user);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
        userChange.setEmail("");
        userChange.setUserName("");
        if(user.getUserName() == userRepository.findAllByUserName(user.getUserName()) ||
                user.getEmail() == userRepository.findAllByEmail(user.getEmail())){
            userRepository.save(user);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "BAD_REQUEST"));
        }
        else  {
            userChange.setEmail(user.getEmail());
            userChange.setName(user.getName());
            userChange.setPhoneNumber(user.getPhoneNumber());
            userChange.setUserName(user.getUserName());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String enCryptPassword = bCryptPasswordEncoder.encode(user.getPassword());
            userChange.setPassword(enCryptPassword);
            userRepository.save(userChange);
        }
        return userChange;
    }

    @Override
    public UserVM getAllJE(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}

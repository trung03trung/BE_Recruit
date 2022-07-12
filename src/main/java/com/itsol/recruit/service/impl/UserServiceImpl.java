package com.itsol.recruit.service.impl;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.repository.repoimpl.UserRepositoryImpl;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.web.vm.SeachVM;
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
    private final UserRepository userRepository;
    private   final UserRepositoryImpl userRepositoryimpl;

    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, UserRepositoryImpl userRepositoryimpl, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRepositoryimpl = userRepositoryimpl;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
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
    public List<User> seachUser(SeachVM seachVM) {
        return userRepositoryimpl.seachUser(seachVM);
    }
}

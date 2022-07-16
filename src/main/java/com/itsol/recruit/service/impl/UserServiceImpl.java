package com.itsol.recruit.service.impl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.repository.repoimpl.UserRepositoryImpl;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.service.mapper.UserMapper;
import com.itsol.recruit.web.vm.SeachVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import com.sun.xml.internal.ws.handler.HandlerException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Getter
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryimpl;

    private final RoleRepository roleRepository;

    public final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserRepositoryImpl userRepositoryimpl, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRepositoryimpl = userRepositoryimpl;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
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
        if (userChange == null) {
            userRepository.save(user);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
        userChange.setEmail("");
        userChange.setUserName("");
        if (user.getUserName() == userRepository.findAllByUserName(user.getUserName()) ||
                user.getEmail() == userRepository.findAllByEmail(user.getEmail())) {
            userRepository.save(user);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "BAD_REQUEST"));
        } else {
            userChange.setEmail(user.getEmail());
            userChange.setName(user.getName());
            userChange.setPhoneNumber(user.getPhoneNumber());
            userChange.setUserName(user.getUserName());
            userRepository.save(userChange);
        }
        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.OK, "ok"));
    }

    @Override
    public List<User> seachUser(SeachVM seachVM) {
        return userRepositoryimpl.seachUser(seachVM);
    }

    @Override
    public Object changeThePassWord(UserDTO user) {
        System.out.println(user);
        User changeUserPassW = userRepository.findByUserName(user.getUserName());

        if (changeUserPassW == null) {
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(user.getPassword(), changeUserPassW.getPassword())) {
            userRepository.save(changeUserPassW);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "BAD_REQUEST"));
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder1 = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder1.encode(user.getNewPassword());
            changeUserPassW.setPassword(encode);
            userRepository.save(changeUserPassW);
        }
        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.OK, "ok"));
    }

    @Override
    public Object deactivateUser(User user) {
        User deactivateUser = userRepository.findById(user.getId()).orElse(null);
        if (deactivateUser == null) {
            userRepository.save(user);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        } else {
            if(deactivateUser.isActivate()){
                deactivateUser.setActivate(false);
            }
            else {
                deactivateUser.setActivate(true);
            }
            userRepository.save(deactivateUser);
        }
        return user;
    }

    @Override
    public ResponseDTO addUserJe(UserDTO dto) {
        User userName = userRepository.findByUserName(dto.getUserName());
        User email = userRepository.findUserByEmail(dto.getEmail());
        if (userName != null) throw new NullPointerException();
        if (email != null) throw new HandlerException("email");
        List<Role> roles = roleRepository.findByCode(Constants.Role.JE);
        User user = userMapper.toEntity(dto);
        user.setActivate(true);
        user.setDelete(false);
        user.setRoles(roles);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String enCryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        user.setPassword(enCryptPassword);
        userRepository.save(user);
        return new ResponseDTO("Signup success");
    }
    public List<StatisticalDTO> statistical(StatisticalVm statisticalVm) {
        return userRepositoryimpl.StatisticalData(statisticalVm);
    }
}

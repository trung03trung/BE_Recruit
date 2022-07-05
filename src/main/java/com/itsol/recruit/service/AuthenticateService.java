package com.itsol.recruit.service;


import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.User;

public interface AuthenticateService {
    public User signup(UserDTO dto);

    public String changePassword(String code,UserDTO userDto);
}

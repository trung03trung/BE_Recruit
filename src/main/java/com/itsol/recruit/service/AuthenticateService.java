package com.itsol.recruit.service;


import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.web.vm.ChangePassVM;

public interface AuthenticateService {
    public User signup(UserDTO dto);

    public ResponseDTO changePassword(ChangePassVM changePassVM);
}

package com.itsol.recruit.service;


import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.dto.respone.TokenResponse;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.web.vm.ChangePassVM;
import com.itsol.recruit.web.vm.LoginVM;

public interface AuthenticateService {
    public ResponseDTO signup(UserDTO dto);

    public ResponseDTO changePassword(ChangePassVM changePassVM);

    public TokenResponse getToken(LoginVM loginVM);
}

package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.OTPRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.service.ActiveService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ActiveServiceImpl implements ActiveService {

    public final OTPRepository otpRepository;

    public final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ActiveServiceImpl(OTPRepository otpRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDTO activeAccount(String otp) {
        OTP otpdb = otpRepository.findByCode(otp);
        User user = otpdb.getUser();
        user.setActivate(true);
        userRepository.save(user);
        return new ResponseDTO("Active success");
    }
}

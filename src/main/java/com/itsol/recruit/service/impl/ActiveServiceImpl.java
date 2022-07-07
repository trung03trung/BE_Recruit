package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.OTPRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.service.ActiveService;
import org.springframework.stereotype.Service;

@Service
public class ActiveServiceImpl implements ActiveService {

    public final OTPRepository otpRepository;

    public final UserRepository userRepository;

    public ActiveServiceImpl(OTPRepository otpRepository, UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String activeAccount(String otp) {
        OTP otpdb= otpRepository.findByCode(otp);
        User user=otpdb.getUser();
        user.setActive(true);
        userRepository.save(user);
        return null;
    }
}

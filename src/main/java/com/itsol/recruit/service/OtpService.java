package com.itsol.recruit.service;

import com.itsol.recruit.dto.UserDTO;

public interface OtpService {
    String sendOTP(String email);
}

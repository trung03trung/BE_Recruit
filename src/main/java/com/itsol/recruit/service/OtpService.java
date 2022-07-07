package com.itsol.recruit.service;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.UserDTO;

public interface OtpService {
   ResponseDTO sendOTP(String email);
}

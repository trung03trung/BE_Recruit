package com.itsol.recruit.service.impl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.AuthenticateRepository;
import com.itsol.recruit.repository.OTPRepository;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.security.jwt.TokenProvider;
import com.itsol.recruit.service.AuthenticateService;
import com.itsol.recruit.service.email.EmailService;
import com.itsol.recruit.service.mapper.UserMapper;
import com.itsol.recruit.web.vm.LoginVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    public final AuthenticateRepository authenticateRepository;

    public final UserMapper userMapper;

    public final RoleRepository roleRepository;

    public final UserRepository userRepository;

    public final OTPRepository otpRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    public AuthenticateServiceImpl(AuthenticateRepository authenticateRepository, UserMapper userMapper, RoleRepository roleRepository, UserRepository userRepository, OTPRepository otpRepository, PasswordEncoder passwordEncoder, EmailService emailService, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.authenticateRepository = authenticateRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public User signup(UserDTO dto) {
        try {
            User userName = userRepository.findByUserName(dto.getUserName());
            User email = userRepository.findUserByEmail(dto.getEmail());
            User phoneNumber = userRepository.findUserByPhoneNumber(dto.getPhoneNumber());
            if (userName != null) {
                return null;
            }
            if (email != null) {
                return null;
            }
            if (phoneNumber != null) {
                return null;
            }
            Set<Role> roles = roleRepository.findByCode(Constants.Role.USER);
            User user = userMapper.toEntity(dto);
            user.setDelete(false);
            user.setActive(false);
            user.setActive(false);
            user.setDelete(false);
            user.setRoles(roles);

            userRepository.save(user);

            OTP otp=new OTP(user);
            otpRepository.save(otp);
            String link="http://localhost:4200/active?code="+otp.getCode();
            String emails=emailService.buildActiveLink(link);
            emailService.sendEmail(user.getEmail(),emails);
            return user;

//        OTP otp = userService.generateOTP(user);
//        String linkActive = accountActivationConfig.getActivateUrl() + user.getId();
//        emailService.sendSimpleMessage(user.getEmail(),
//                "Link active account",
//                "<a href=\" " + linkActive + "\">Click vào đây để kích hoạt tài khoản</a>");
        } catch (Exception e) {
            log.error("cannot save to database");
            return null;
        }

    }

    @Override
    public String changePassword(String code, UserDTO userDto) {
        String message;
        User user = userRepository.findUserByEmail(userDto.getEmail());
        if (user != null) {
            OTP optdb = otpRepository.findByUser(user);
            if (optdb.isExpired())
                message = "Mã otp đã hết hạn";
            else if (optdb.getCode().equals(code)) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepository.save(user);
                message = "Đổi mật khẩu thành công";
            } else {
                message = "Mã otp không chính xác";
            }
        } else {
            message = "Không thể đổi mật khẩu";
        }
        return message;


    }
}

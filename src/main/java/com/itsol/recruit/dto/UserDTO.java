package com.itsol.recruit.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserDTO {
    String name;

    String fullName;

    String email;

    String userName;

    String password;

    String phoneNumber;

    String homeTown;

    String avatarName;

    String gender;

    //Boolean activate;

    //Number is_delete;

    String newPassword;

    Date birthDay;
}

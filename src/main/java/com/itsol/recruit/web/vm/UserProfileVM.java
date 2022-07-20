package com.itsol.recruit.web.vm;

import lombok.Data;

import java.util.Date;

@Data
public class UserProfileVM {
    Long id;

    String name;

    String email;

    String phoneNumber;

    String homeTown;

    String avatarName;

    String gender;

    //Boolean activate;

    //Number is_delete;

    Date birthDay;
}

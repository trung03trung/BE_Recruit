package com.itsol.recruit.dto.respone;

import com.itsol.recruit.entity.Role;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserResponse {
    Long id;

    String name;

    String email;

    String userName;

    String phoneNumber;

    String homeTown;


    String image;


    String gender;

    Date birthDay;


    private boolean activate;


    boolean isDelete;


}

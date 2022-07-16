package com.itsol.recruit.entity;

import com.itsol.recruit.core.Constants;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@Entity
@Data
public class OTP implements Serializable {
    private final static Long EXPIRED_TIME= Constants.OTP.EXPIRED_TIME;
    private static final Long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_OTP_ID")
    @SequenceGenerator(name = "GEN_OTP_ID", sequenceName = "SEQ_OTP", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(name = "issue_at",nullable = false)
    private Date issueAt;

    @OneToOne()
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public OTP(User user){
        this.user=user;
        Random r=new Random();
        int number=(10000+r.nextInt(90000));
        this.code=String.valueOf(number);
         this.issueAt=new Date();
    }

    public OTP(){
        Random r=new Random();
        int number=(10000+r.nextInt(90000));
        this.code=String.valueOf(number);
        this.issueAt=new Date();
    }

    public boolean isExpired(){
        return this.issueAt.getTime()+EXPIRED_TIME<new Date().getTime();
    }
}

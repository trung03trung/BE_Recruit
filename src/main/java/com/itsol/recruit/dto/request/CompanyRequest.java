package com.itsol.recruit.dto.request;

;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CompanyRequest {
    String name;

    @NotEmpty
    String email;

    @NotEmpty
    String hotLine;


    Date dateIncoporation;

    @NotEmpty
    String headOffice;

    @NotNull
    Integer numberStaff ;

    @NotEmpty
    String  linkWeb ;

    @NotEmpty
    String  description ;

    @NotNull
    MultipartFile avatar;

    String  backdropImg ;
}

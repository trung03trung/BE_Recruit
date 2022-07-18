package com.itsol.recruit.dto;
import com.itsol.recruit.entity.*;
import lombok.*;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    @NonNull
    Long id;

    @NonNull
    @Size(max = 200)
    String name;

    @NonNull
    @Size(max = 100)
    String email;

    @NonNull
    @Size(max = 100)
    String hotline;

    @NonNull
    Date date_incoporation;

    @NonNull
    @Size(max = 100)
    String tax_code;

    @NonNull
    Date tax_date;

    @NonNull
    String tax_place;

    @NonNull
    String head_office;

    @NonNull
    Integer number_staff;

    @NonNull
    String link_web;

    @NonNull
    @Size(max = 100)
    String description;

    @NonNull
    String avatar;

    @NonNull
    String backdrop_img;

    @NonNull
    Integer is_delete;
}

package com.itsol.recruit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseDTO {
    HttpStatus status;
    String code;

    public ResponseDTO(String code){
        this.code=code;
    }

    public ResponseDTO(HttpStatus status,String code){
        this.status=status;
        this.code=code;
    }
}

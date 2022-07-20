package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.service.impl.ImageService;
import com.itsol.recruit.web.vm.SeachVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)

public class UserPublicController {

    public final UserService userService;

    private final ImageService imageService;

    public UserPublicController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping(value = "/user/{userName}")
    public ResponseEntity<User> findUserByUserName(@RequestParam("userName") String userName) {
        return ResponseEntity.ok().body(userService.findUserByUserName(userName));
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> findUserById(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @PostMapping(value = "userSeach")
    public ResponseEntity<List<User>> seachUser(@RequestBody SeachVM seachVM) {
        return ResponseEntity.ok().body(userService.seachUser(seachVM));
    }

    @PutMapping(value = "changeThePassWord")
    public ResponseEntity<Object> changThePassWord(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.changeThePassWord(userDTO));
    }

    @PostMapping(value = "/upload/image")
    public ResponseEntity<ResponseDTO> uploadImage(@RequestParam("image") MultipartFile file){
        try {
            ResponseDTO responseDTO=imageService.uploadImage(file);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.NOT_FOUND,"Fail upload file"));
        }
    }
    @GetMapping(value = "/number-user-je")
    public ResponseEntity<Integer> getCountUserJe(){
        return ResponseEntity.ok().body(userService.getNumberUserJe());
    }

}

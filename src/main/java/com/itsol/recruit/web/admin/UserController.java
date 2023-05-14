package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.dto.respone.ColumnChartResponse;
import com.itsol.recruit.dto.respone.LineChartDataResponse;
import com.itsol.recruit.entity.Image;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.service.impl.ImageService;
import com.itsol.recruit.web.vm.SeachVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import com.itsol.recruit.web.vm.UserProfileVM;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.ADMIN)
public class UserController {

    public final UserService userService;

    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping(value = "/user/{id}1")
    public ResponseEntity<User> findUserById(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping(value = "/user-profile")
    public ResponseEntity<User> findUserByUserName(@RequestParam("userName") String userName) {
        return ResponseEntity.ok().body(userService.findUserByUserName(userName));
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

    @PutMapping(value = "deactivateUser")
    public ResponseEntity<Object> deactivateUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.deactivateUser(user));
    }

    @PostMapping("/addUserJe")
    public ResponseEntity<ResponseDTO> addUserJe(@Valid @RequestBody UserDTO dto) {
        try {
            ResponseDTO responseDTO = userService.addUserJe(dto);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        } catch (NullPointerException e) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.NOT_FOUND, "Username exist"));
        } catch (HandlerException e) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.NO_CONTENT, "Email exist"));
        }
    }
    @PostMapping(value = "/statistical")
    public ResponseEntity<List<StatisticalDTO>> statistical(@Valid @RequestBody StatisticalVm statisticalVm){
        return ResponseEntity.ok().body(userService.statistical(statisticalVm));
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


    @GetMapping(value = "/image/{name}")
    public ResponseEntity<Image> getImageDetail(@PathVariable("name") String name){
        try {
            return ResponseEntity.ok().body(imageService.getImageByName(name));
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/user/update")
    public ResponseEntity<User> updateUserProfile(@RequestBody UserProfileVM userVM){
        return ResponseEntity.ok().body(userService.saveUser(userVM));
    }

    @PostMapping(value = "/line-chart")
    public ResponseEntity<LineChartDataResponse> getDataLineChart(@RequestBody StatisticalVm request){
        return ResponseEntity.ok().body(userService.getDataLineChart(request));
    }

    @PostMapping(value = "/column-chart")
    public ResponseEntity<ColumnChartResponse> getDataColumnChart(@RequestBody StatisticalVm request){
        return ResponseEntity.ok().body(userService.getDataColumnChart(request));
    }

}

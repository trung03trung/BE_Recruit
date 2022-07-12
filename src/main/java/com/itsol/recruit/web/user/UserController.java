package com.itsol.recruit.web.user;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.web.vm.UserVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)

public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> findUserById(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping(value = "/user/{userName}")
    public ResponseEntity<User> findUserByUserName(@RequestParam("userName") String userName) {
        return ResponseEntity.ok().body(userService.findUserByUserName(userName));
    }

    @GetMapping(value = "/userje")
    public ResponseEntity<List<User>> getAllUserJE() {
        return ResponseEntity.ok().body(userService.getAllUserJe());
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @PutMapping(value = "/hoang")
    public ResponseEntity<UserVM> getAllJob(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "dueDate",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir ){
        return ResponseEntity.ok().body(userService.getAllJE(pageNo,pageSize,sortBy,sortDir));
    }

}

package com.example.PMS.users.controller;

import com.example.PMS.users.dto.request.UserRequest;
import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.users.dto.response.UserResponse;
import com.example.PMS.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse<UserResponse>> create(
            @Valid @RequestBody UserRequest request
            ){
        UserResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.created("User created successfully.",response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponse>>> getAll(){
        List<UserResponse> response = userService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Users retrieved successfully",response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getById(
            @PathVariable Integer id
    ){
        UserResponse response = userService.findById(id);
        return  ResponseEntity.ok(
                BaseResponse.ok("User retrieved successfully",response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest request){
        UserResponse response = userService.update(id,request);
        return ResponseEntity.ok(
                BaseResponse.ok("User updated successfully.",response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id){
        userService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("User deleted successfully.",null)
        );
    }
}

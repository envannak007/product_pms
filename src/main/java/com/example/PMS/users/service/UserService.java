package com.example.PMS.users.service;

import com.example.PMS.users.dto.request.UserRequest;
import com.example.PMS.users.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create (UserRequest request);
    UserResponse findById(Integer id);
    List<UserResponse> findAll();
    UserResponse update(Integer id,UserRequest request);
    void delete(Integer id);
}

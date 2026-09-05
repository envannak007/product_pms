package com.example.PMS.service;

import com.example.PMS.dto.request.UserRequest;
import com.example.PMS.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create (UserRequest request);
    UserResponse findById(Integer id);
    List<UserResponse> findAll();
    UserResponse update(Integer id,UserRequest request);
    void delete(Integer id);
}

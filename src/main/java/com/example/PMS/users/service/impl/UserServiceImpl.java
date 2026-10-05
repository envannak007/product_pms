package com.example.PMS.users.service.impl;

import com.example.PMS.users.dto.request.UserRequest;
import com.example.PMS.users.dto.response.UserResponse;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.users.mapper.UserMapper;
import com.example.PMS.users.repository.UserRepository;
import com.example.PMS.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {
        boolean check = userRepository.existsByEmail(request.email());
        if (check){
            throw new ResourceNotFoundException("MMMMMMMMM");
        }
        UserEntity user = userMapper.toEntity(request);
        UserEntity saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse findById(Integer id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(
                        "User not found with id : " + id
                ));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse update(Integer id, UserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(
                        "User not found with id : " +id
                ));
        userMapper.updateEntity(user,request);
        UserEntity updated = userRepository.save(user);
        return userMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(
                        "User not found with id : " + id
                ));
        userRepository.delete(user);
    }
}

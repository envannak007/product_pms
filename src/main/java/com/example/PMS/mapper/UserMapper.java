package com.example.PMS.mapper;

import com.example.PMS.dto.request.UserRequest;
import com.example.PMS.dto.response.UserResponse;
import com.example.PMS.entity.UserEntity;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public  UserEntity toEntity(UserRequest request){
        UserEntity user = new UserEntity();
        user.setUserName(request.userName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        return user;
    }

    public UserResponse toResponse(UserEntity user){
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreateAt(),
                user.getUpdateAt()
        );
    }

    public void updateEntity(
            UserEntity user,
            UserRequest request
    ){
        user.setUserName(request.userName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setUpdateAt(LocalDateTime.now());
    }
}

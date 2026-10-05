package com.example.PMS.users.mapper;

import com.example.PMS.users.dto.request.UserRequest;
import com.example.PMS.users.dto.response.UserResponse;
import com.example.PMS.users.entitty.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public  UserEntity toEntity(UserRequest request){
        UserEntity user = new UserEntity();
        user.setUsername(request.userName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        return user;
    }

    public UserResponse toResponse(UserEntity user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreateAt(),
                user.getUpdateAt()
        );
    }

    public void updateEntity(
            UserEntity user,
            UserRequest request
    ){
        user.setUsername(request.userName());
        user.setEmail(request.email());
        user.setPassword(request.password());
    }
}

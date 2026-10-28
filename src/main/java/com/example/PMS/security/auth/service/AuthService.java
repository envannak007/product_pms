package com.example.PMS.security.auth.service;

import com.example.PMS.security.auth.dto.request.LoginRequest;
import com.example.PMS.security.auth.dto.request.RegisterRequest;
import com.example.PMS.security.auth.dto.response.LoginResponse;
import com.example.PMS.security.auth.dto.response.RegisterResponse;
import com.example.PMS.security.auth.mapper.AuthMapper;
import com.example.PMS.security.jwt.JwtService;
import com.example.PMS.users.entitty.RoleEntity;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.users.enums.RoleType;
import com.example.PMS.users.repository.RoleRepository;
import com.example.PMS.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login (LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return authMapper.toLoginResponse(userDetails,token);
    }

    // register
    @Transactional
    public RegisterResponse register(RegisterRequest request){
        // check name
        if (userRepository.existsByUsername(request.username())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        //check email
        if (userRepository.existsByEmail(request.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        // find default user role
        RoleEntity userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "User role not found. Please initialize role first"
                ));

        // create user
        UserEntity user = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .enabled(true)
                .roles(new HashSet<>())
                .build();

        // Assign user role
        user.getRoles().add(userRole);

        // save user
        UserEntity saveUser = userRepository.save(user);

        // return response
        return authMapper.toRegisterResponse(saveUser);
    }
}

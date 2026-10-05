package com.example.PMS.security.config;

import com.example.PMS.users.entitty.RoleEntity;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.users.enums.RoleType;
import com.example.PMS.users.repository.RoleRepository;
import com.example.PMS.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            RoleEntity userRole = roleRepository
                    .findByName(RoleType.USER)
                    .orElseGet(() ->
                            roleRepository.save(
                                    new RoleEntity(RoleType.USER)
                            )
                    );

            RoleEntity adminRole = roleRepository
                    .findByName(RoleType.ADMIN)
                    .orElseGet(() ->
                            roleRepository.save(
                                    new RoleEntity(RoleType.ADMIN)
                            )
                    );

            userRepository
                    .findByUsername("admin")
                    .orElseGet(() -> {

                        UserEntity user = new UserEntity();

                        user.setUsername("admin");
                        user.setEmail("admin007@gmail.com");
                        user.setPassword(
                                passwordEncoder.encode("007007")
                        );

                        user.getRoles().add(userRole);
                        user.getRoles().add(adminRole);

                        return userRepository.save(user);
                    });
        };
    }
}
package com.example.PMS.users.entitty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 100)
    private String username;

    @Column(nullable = false,unique = true,length = 150)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    // Backwards Compatibility Helpers for UserMapper & Existing DTOs
    public String getName() {
        return this.username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getRoel() {
        if (roles != null && !roles.isEmpty()) {
            return roles.iterator().next().getName().name();
        }
        return "ROLE_USER";
    }

    public void setRoel(String roel) {
        // Compatibility setter
    }

    public LocalDateTime getCreateAt() {
        return this.createdAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return this.updatedAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updatedAt = updateAt;
    }

}

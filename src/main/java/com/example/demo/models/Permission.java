package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permission")
    private String permission;

    @Override
    public String getAuthority() {
        return permission;
    }
}
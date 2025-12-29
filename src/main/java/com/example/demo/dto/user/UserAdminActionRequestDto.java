package com.example.demo.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminActionRequestDto {

    private Long userId;
    private String action;
}
package com.example.demo.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequestDto {

    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
}
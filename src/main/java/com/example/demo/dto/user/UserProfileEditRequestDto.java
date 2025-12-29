package com.example.demo.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEditRequestDto {

    private String fullName;
    private String email;
}
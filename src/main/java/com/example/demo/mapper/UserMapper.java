package com.example.demo.mapper;

import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(mapRolesToStrings(user.getRoles()))")
    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDtoList(List<User> users);

    // Ручной метод для преобразования ролей в строки
    default List<String> mapRolesToStrings(List<com.example.demo.models.Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(p -> p.getPermission())
                .toList();
    }
}
package com.example.demo.mapper;

import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.models.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-29T04:28:06+0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 24 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto.UserResponseDtoBuilder userResponseDto = UserResponseDto.builder();

        userResponseDto.id( user.getId() );
        userResponseDto.email( user.getEmail() );
        userResponseDto.fullName( user.getFullName() );

        userResponseDto.roles( mapRolesToStrings(user.getRoles()) );

        return userResponseDto.build();
    }

    @Override
    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( users.size() );
        for ( User user : users ) {
            list.add( toResponseDto( user ) );
        }

        return list;
    }
}

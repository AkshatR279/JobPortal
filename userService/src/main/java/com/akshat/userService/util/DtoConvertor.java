package com.akshat.userService.util;

import com.akshat.userService.model.dto.UserResponseShortDto;
import com.akshat.userService.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConvertor {
    public UserResponseShortDto convertToUserShort(User user){
        return UserResponseShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}

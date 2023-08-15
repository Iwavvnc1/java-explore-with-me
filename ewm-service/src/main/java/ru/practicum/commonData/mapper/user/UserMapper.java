package ru.practicum.commonData.mapper.user;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.model.user.User;
import ru.practicum.commonData.model.user.dto.NewUserDto;
import ru.practicum.commonData.model.user.dto.UserDto;
import ru.practicum.commonData.model.user.dto.UserShortDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {
    public User toUserFromNewUserDto(NewUserDto userDto){
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
    public UserDto toUserDtoFromUser(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserShortDto toUserShortDtoFromUser(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
    public List<UserDto> toUserDtoListFromListUsers(List<User> users) {
        return users.stream().map(UserMapper::toUserDtoFromUser).collect(Collectors.toList());
    }
}

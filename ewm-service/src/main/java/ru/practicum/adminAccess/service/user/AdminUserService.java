package ru.practicum.adminAccess.service.user;

import ru.practicum.commonData.model.user.dto.NewUserDto;
import ru.practicum.commonData.model.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    UserDto createUser(NewUserDto userDto);

    void deleteUser(Long userId);

    List<UserDto> getUsers(List<Long> ids, int from, int size);
}

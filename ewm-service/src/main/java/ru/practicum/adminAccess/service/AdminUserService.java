package ru.practicum.adminAccess.service;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.user.dto.NewUserDto;
import ru.practicum.commonData.model.user.dto.UserDto;

import java.util.List;

@Service
public class AdminUserService {
    public UserDto createUser(NewUserDto userDto) {
        return null;
    }

    public void deleteUser(Long userId) {

    }

    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        return null;
    }
}

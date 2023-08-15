package ru.practicum.adminAccess.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.user.dto.NewUserDto;
import ru.practicum.commonData.model.user.dto.UserDto;
import ru.practicum.commonData.repository.UserRepository;
import ru.practicum.commonData.shtoto.CustomPageRequest;

import java.util.List;

import static ru.practicum.commonData.mapper.user.UserMapper.*;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    public UserDto createUser(NewUserDto userDto) {
        return toUserDtoFromUser(userRepository.save(toUserFromNewUserDto(userDto)));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
            return toUserDtoListFromListUsers(userRepository.getAllByIdIn(ids, CustomPageRequest.of(from,size)).toList());
    }
}

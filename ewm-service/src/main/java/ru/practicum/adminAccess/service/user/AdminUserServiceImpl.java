package ru.practicum.adminAccess.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.model.user.User;
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

    @Transactional
    public UserDto createUser(NewUserDto userDto) {
        User user;
        try {
            user = userRepository.save(toUserFromNewUserDto(userDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Email is busy");
        }
        return toUserDtoFromUser(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(String.format("User with id=%d was not found", userId));
        }
    }

    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        return toUserDtoListFromListUsers(userRepository.getAllByIdIn(ids, CustomPageRequest.of(from, size)).toList());
    }
}

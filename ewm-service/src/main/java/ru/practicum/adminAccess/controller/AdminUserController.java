package ru.practicum.adminAccess.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminAccess.service.user.AdminUserServiceImpl;
import ru.practicum.commonData.model.user.dto.NewUserDto;
import ru.practicum.commonData.model.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final AdminUserServiceImpl service;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid NewUserDto userDto) {
        log.info("Request POST /admin/users with dto = {}", userDto);
        return new ResponseEntity<>(service.createUser(userDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
        log.info("Request DELETE /admin/users/{}", userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) List<Long> ids,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info("Request GET /admin/users with ids = {}, from = {}, size = {}", ids, from, size);
        return new ResponseEntity<>(service.getUsers(ids, from, size), HttpStatus.OK);
    }
}

package ru.practicum.commonData.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.commonData.model.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> getAllByIdIn(List<Long> ids, Pageable pageable);
}

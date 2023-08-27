package ru.practicum.commonData.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.commonData.model.compilation.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query("SELECT c " +
            "FROM Compilation c " +
            "WHERE c.pinned IN ?1 or ?1 is null")
    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}

package ru.practicum.adminAccess.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.exceptions.ConditionsNotMatch;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.model.category.Category;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.category.dto.NewCategoryDto;
import ru.practicum.commonData.repository.CategoryRepository;
import ru.practicum.commonData.repository.EventRepository;


import static ru.practicum.commonData.mapper.category.CategoryMapper.*;

@RequiredArgsConstructor
@Service
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Transactional
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        Category category;
        try {
            category = categoryRepository.save(toCategory(categoryDto));
            categoryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
        return toCategoryDto(category);
    }

    @Transactional
    public void deleteCategory(Long catId) {
        if (eventRepository.existsByCategoryId(catId)) {
            throw new ConditionsNotMatch("Category not empty");
        }
        categoryRepository.deleteById(catId);
    }

    @Transactional
    public CategoryDto update(Long catId, NewCategoryDto categoryDto) {
        Category category = toCategory(categoryDto);
        category.setId(catId);
        try {
        category = categoryRepository.save(category);
        categoryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage(), e);
        }
        return toCategoryDto(category);
    }
}

package ru.practicum.adminAccess.service;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.category.dto.NewCategoryDto;

@Service
public class AdminCategoriesService {
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        return null;
    }

    public void deleteCategory(Long catId) {
    }

    public CategoryDto update(Long catId, NewCategoryDto categoryDto) {
        return null;
    }
}

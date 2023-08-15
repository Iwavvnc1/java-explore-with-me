package ru.practicum.adminAccess.service.category;

import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.category.dto.NewCategoryDto;

public interface AdminCategoriesService {
    CategoryDto createCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long catId);

    CategoryDto update(Long catId, NewCategoryDto categoryDto);
}

package ru.practicum.adminAccess.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.category.Category;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.category.dto.NewCategoryDto;
import ru.practicum.commonData.repository.CategoryRepository;

import static ru.practicum.commonData.mapper.category.CategoryMapper.*;

@RequiredArgsConstructor
@Service
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final CategoryRepository categoryRepository;
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        return toCategoryDto(categoryRepository.save(toCategory(categoryDto)));
    }

    public void deleteCategory(Long catId) {
        categoryRepository.deleteById(catId);
    }

    public CategoryDto update(Long catId, NewCategoryDto categoryDto) {
        Category category = toCategory(categoryDto);
        category.setId(catId);
        return toCategoryDto(categoryRepository.save(category));
    }
}

package ru.practicum.commonData.mapper.category;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.model.category.Category;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.category.dto.NewCategoryDto;

@UtilityClass
public class CategoryMapper {
    public Category toCategory(NewCategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

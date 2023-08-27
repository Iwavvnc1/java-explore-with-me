package ru.practicum.publicAccess.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.mapper.category.CategoryMapper;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.repository.CategoryRepository;
import ru.practicum.commonData.utils.customPageRequest.CustomPageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll(CustomPageRequest.of(from, size)).stream()
                .map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId))));
    }
}

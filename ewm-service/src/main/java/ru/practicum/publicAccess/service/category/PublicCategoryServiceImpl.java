package ru.practicum.publicAccess.service.category;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.category.dto.CategoryDto;

import java.util.List;

@Service
public class PublicCategoryServiceImpl implements PublicCategoryService {
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return null;
    }

    public CategoryDto getCategoryById(Long catId) {
        return null;
    }
}

package ru.practicum.adminAccess.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminAccess.service.AdminCategoriesService;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.category.dto.NewCategoryDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/admin/categories")
public class AdminCategoriesController {
    private final AdminCategoriesService service;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto categoryDto) {
        return new ResponseEntity<>(service.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity deleteCategory(@PathVariable Long catId) {
        service.deleteCategory(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long catId,
                                                      @RequestBody @Valid NewCategoryDto categoryDto) {
        return new ResponseEntity<>(service.update(catId, categoryDto), HttpStatus.OK);
    }
}

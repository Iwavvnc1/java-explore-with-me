package ru.practicum.publicAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.publicAccess.service.category.PublicCategoryServiceImpl;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoryController {
    private final PublicCategoryServiceImpl service;
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request GET /categories with from = {}, size = {}", from, size);
        return new ResponseEntity<>(service.getCategories(from, size), HttpStatus.OK);
    }
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long catId) {
        log.info("Request GET /categories/{}", catId);
        return new ResponseEntity<>(service.getCategoryById(catId), HttpStatus.OK);
    }
}

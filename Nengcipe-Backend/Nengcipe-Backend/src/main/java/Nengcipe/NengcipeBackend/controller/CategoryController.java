package Nengcipe.NengcipeBackend.controller;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.dto.CategoryDto;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResultResponse> registerCategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryService.registerCategory(categoryDto);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("카테고리 등록 성공.")
                .result(category).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResultResponse> getCategory(@RequestParam Long id) throws NotFoundException {
        Category category = categoryService.findCategoryById(id);
        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("카테고리 등록 성공.")
                .result(category).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}

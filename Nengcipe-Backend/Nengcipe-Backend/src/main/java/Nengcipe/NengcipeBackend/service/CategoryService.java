package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.dto.CategoryDto;
import Nengcipe.NengcipeBackend.exception.DuplicationException;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    /** 새로운 카테고리 등록. 같은 이름이 존재한다면 DuplicationException 발생.  */
    public Category registerCategory(CategoryDto categoryDto) {
        Optional<Category> findCategory = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (findCategory.isPresent()) {
            throw new DuplicationException("카테고리 이름",categoryDto);
        }
        Category category = Category.builder().categoryName(categoryDto.getCategoryName()).build();
        categoryRepository.save(category);
        return category;
    }

    /**
     * 재료에 등록할 카테고리 찾기.
     * 찾지 못하면 등록하고 반환.
     * */
    public Category findCategoryByName(String name) throws NotFoundException {
        Optional<Category> category = categoryRepository.findByCategoryName(name);
        if (category.isEmpty()) {
            Category cate = Category.builder().categoryName(name).build();
            categoryRepository.save(cate);
            return categoryRepository.findByCategoryName(name).get();
        }
        return category.get();
    }
    /** 재료에 등록할 카테고리 찾기. 찾지 못하면 NotFoundException 발생.  */
    public Category findCategoryById(Long id) throws NotFoundException {

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("카테고리 이름", null);
        }
        return category.get();
    }


}

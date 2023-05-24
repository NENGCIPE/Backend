package Nengcipe.NengcipeBackend.service;


import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;


    public Recipe findRecipeById(Long id) throws NotFoundException {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        /** recipe를 찾지 못한다면, NotFoundException 발생 **/
        if (recipe.isEmpty()) {
            throw new NotFoundException("레시피", null);
        }
        return recipe.get();
    }
}

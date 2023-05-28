package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.repository.IngredientRepository;
import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    // 유저가 저장한 냉장고 재료 목록을 Recipe의 재료 목록과 비교하여 matchingRecipes에 저장.
    public List<Recipe> findMatchingRecipes() {

        List<Ingredient> ingredients = ingredientRepository.findAllByIngredNameIsNotNull();
        List<String> recipeIngredients = recipeRepository.findAllByrecipeIngredNameIsNotNull();
        List<Recipe> matchingRecipes = new ArrayList<>();

        for (String recipeIngredient : recipeIngredients) {
            for (Ingredient ingredient : ingredients) {
                if (recipeIngredient.equals(ingredient.getIngredName())) {
                    List<Recipe> recipes = recipeRepository.findAll();
                    matchingRecipes.addAll(recipes);
                }
            }
        }
        return matchingRecipes;
    }
}

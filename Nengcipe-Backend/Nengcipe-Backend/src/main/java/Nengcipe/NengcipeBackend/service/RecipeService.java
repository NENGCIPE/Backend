package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.IngredientDto;
import Nengcipe.NengcipeBackend.dto.MatchingRecipeResponseDto;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.IngredientRepository;
import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> findMatchingRecipes(Member member, List<Ingredient> ingredient) throws NotFoundException {

        List<Recipe> CrawlingRecipeList = recipeRepository.findAll();
        List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipeIngredient : CrawlingRecipeList) {
            boolean flag = false;
            for (Ingredient ingredientNameList : ingredient) {
                String[] ingredArr = recipeIngredient.getRecipeIngredName().toString().split(",");
                for (String value : ingredArr) {
                    if (value.equals(ingredientNameList.getIngredName())) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    matchingRecipes.add(recipeIngredient);
                    break;
                }
            }
        }


        return matchingRecipes;
    }

/*
    public Recipe getRecipeById(Long recipeId, Member member) throws NotFoundException {
        Optional<Recipe> recipeInfo = recipeRepository.findById(recipeId);
        return recipeInfo.get();
    }
*/

    public Recipe findRecipeById(Long id) throws NotFoundException {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        /** recipe를 찾지 못한다면, NotFoundException 발생 **/
        if (recipe.isEmpty()) {
            throw new NotFoundException("레시피", null);
        }
        return recipe.get();
    }
}



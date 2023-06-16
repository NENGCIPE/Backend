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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    /**
     * 내 재료에 맞는 레시피를 랜덤으로 10개 추출
     */
    public List<Recipe> findMatchingRecipes(Member member, List<Ingredient> ingredient) throws NotFoundException {

        List<Recipe> CrawlingRecipeList = recipeRepository.findAll();
        List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipeIngredient : CrawlingRecipeList) {
            boolean flag = false;
            for (Ingredient ingredientNameList : ingredient) {
                String[] ingredArr = recipeIngredient.getRecipeIngredName().split(",");
                for (String value : ingredArr) {
                    if (ingredientNameList.getIngredName().contains(value)) {
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
        ArrayList<Recipe> randomList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(matchingRecipes.size());
            Recipe randomRecipe = matchingRecipes.get(randomInt);
            randomList.add(randomRecipe);
            matchingRecipes.remove(randomInt);

        }


        return randomList;
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



package Nengcipe.NengcipeBackend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:/application.properties")
class CrawlingRecipeServiceTest {

    @Autowired
    private CrawlingRecipeService crawlingRecipeService;

    @Test
    @DisplayName("DB에 올려보자.")
    @Rollback
    public void dbTest() {
      //  crawlingRecipeService.crawlingRecipes(member);

/*
        Recipe recipe = new Recipe();
        recipeService.crawlingRecipes(recipe);
        Recipe save = recipeRepository.save(recipe);
        System.out.println("save.getRecipeName() = " + save.getRecipeName());*/

//        recipeRepository .findById(save.getId());
//        System.out.println("save.getImgUrl() = " + save.getImgUrl());
//        recipe = recipeService.crawlingRecipes(recipe);
//        recipeRepository.save(recipe);
    }
}

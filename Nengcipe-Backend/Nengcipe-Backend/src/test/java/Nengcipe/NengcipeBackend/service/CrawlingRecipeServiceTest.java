package Nengcipe.NengcipeBackend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Random;

@SpringBootTest
//@TestPropertySource(locations = "classpath:/application.properties")
class CrawlingRecipeServiceTest {


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
        Random random = new Random();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("hello");
        strings.add("hello2");
        strings.add("hello3");
        strings.add("hello4");
        strings.add("hello5");
        strings.add("hello6");
        strings.add("hello7");
        strings.add("hello8");
        strings.add("hello9");
        strings.add("hello10");
        strings.add("hello11");
        strings.add("hello12");
        strings.add("hello13");
        strings.add("hello14");
        strings.add("hello15");
        strings.add("hello16");
        strings.add("hello17");
        int hi = 10;
        for (int i = 0; i <= 15; i++) {
            int size = strings.size();
            int ran = random.nextInt(size);
            System.out.println(strings.get(ran));
            strings.remove(ran);
        }
    }
}

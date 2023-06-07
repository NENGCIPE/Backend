package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CrawlingRecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public void crawlingRecipes() {
        // 시작 페이지와 종료 페이지 설정
        int startPage = 6999000;
        int endPage = 6999010;

        for (int page = startPage; page <= endPage; page++) {
            String url = "https://www.10000recipe.com/recipe/" + page;

            try {
                Document doc = Jsoup.connect(url).get();

                /* ======= 레시피명 ============ */
                String recipeName;
                Elements recipeNameElements = doc.select("div.view2_summary.st3 h3");

                if (!recipeNameElements.isEmpty()) {
                    Element nameElement = recipeNameElements.first();
                    recipeName = nameElement.text();
                } else {
                    recipeName = "null";
                }

                /*  ======= 레시피 재료와 수량 추출 ======= */
                StringBuilder recipeIngredName = new StringBuilder();
                StringBuilder recipeIngredAmount = new StringBuilder();

                Elements ingredElement = doc.getElementsByClass("ready_ingre3");
                Elements ingredients = ingredElement.select("li");

                for (Element e : ingredients) {
                    Element spanTag = e.selectFirst("span");
                    Element aTag = e.selectFirst("a[href*=viewMaterial]");

                    // onclick에 저장된 재료명들 파싱
                    if (spanTag != null) {
                        String onclickValue = aTag.attr("onclick");
                        String[] parts = onclickValue.split(",");
                        String ingredText = parts[parts.length - 1];


                        if (recipeIngredName.length() > 0) {
                            recipeIngredName.append(","); // 쉼표 추가
                            recipeIngredAmount.append(","); // 쉼표 추가
                        }

                        recipeIngredName.append(ingredText.substring(2, ingredText.length() - 3));
                        recipeIngredAmount.append(spanTag.text());
                    }
                    else {
                        recipeIngredName.append("");
                        recipeIngredAmount.append("");
                    }
                }

                /* ========= 레시피 상세 정보  =========== */
                Elements stepElements = doc.getElementsByClass("view_step");
                Elements stepDivElements = stepElements.select("div[id*=stepDiv]");

                StringBuilder stepDetails = new StringBuilder();
                for (Element e : stepDivElements) {
                    String recipeStep = e.text();
                    stepDetails.append(recipeStep).append("\n");
                }
                String recipeDetails = stepDetails.toString();

                /* ========= 레시피 이미지  =========== */
                Elements imgElements = doc.getElementsByClass("centeredcrop");
                Elements recipeImg = imgElements.select("img");
                String imgUrl;

                // src 태그에서 url 추출
                if (!recipeImg.isEmpty()) {
                    Element imgElement = recipeImg.first();
                    imgUrl = imgElement.attr("src");
                    imgUrl = imgUrl.toString();
                } else {
                    imgUrl = "null";
                }

                Recipe recipe = Recipe.builder()
                                .recipeName(recipeName).recipeDetail(recipeDetails)
                                .recipeIngredName(recipeIngredName).recipeIngredAmount(recipeIngredAmount)
                                .imgUrl(imgUrl)
                                .build();

                if (!recipeRepository.existsByRecipeName(recipeName)) {
                    recipeRepository.save(recipe);
                }

            } catch (IOException e) {
                System.out.println("Error occurred while scraping recipe from URL: " + url);
                e.printStackTrace();
            }
        }
    }
}


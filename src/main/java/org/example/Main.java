package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        int startPage = 6999000; // 시작 페이지
        int endPage =6999010; // 종료 페이지

        for (int page = startPage; page <= endPage; page++) {

            String url = "https://www.10000recipe.com/recipe/" + page;

            try {
                Document doc = Jsoup.connect(url).get();

                /* ======= 레시피명 ============ */
                String recipeName;
                Elements recipeNameElements = doc.select("div.view2_summary.st3 h3");

                if (!recipeNameElements.isEmpty()){
                    Element nameElement = recipeNameElements.first();
                    recipeName = nameElement.text();
                }
                else {
                    recipeName = "null";
                }

               // System.out.println(recipeName);

                /*  ======= 레시피 재료 ======= */
                String ingredName = "";
                String ingredAmount = "";
                StringBuilder sb = new StringBuilder();


                Elements ingredElement = doc.getElementsByClass("ready_ingre3");
                Elements ingredients = ingredElement.select("li");

                for (Element e : ingredients) {
                    Element spanTag = e.selectFirst("span");
                    Element aTag = e.selectFirst("a[href*=viewMaterial]");

                    if (spanTag != null) {
                        String onclickValue = aTag.attr("onclick");
                        String[] parts = onclickValue.split(",");
                        String ingredText = parts[parts.length-1];

                        ingredName = ingredText.substring(2, ingredText.length()-3);
                        ingredAmount = spanTag.text();
                        sb.append(ingredName);
                        sb.append(ingredAmount);

                    }
                }
                System.out.println(sb.toString());

                /* ========= 레시피 상세 정보  =========== */
                Elements stepElements = doc.getElementsByClass("view_step");
                Elements stepDivElements = stepElements.select("div[id*=stepDiv]");

                for (Element e: stepDivElements) {
                    String recipeStep = e.text();
                   //  System.out.println(recipeStep);
                }

                /* ========= 레시피 이미지  =========== */
                Elements imgElements = doc.getElementsByClass("centeredcrop");
                Elements recipeImg = imgElements.select("img");
                String imgUrl;

                if (!recipeImg.isEmpty()) {
                    Element imgElement = recipeImg.first();
                    imgUrl = imgElement.attr("src");
                } else {
                    imgUrl = "null";
                }

             //  System.out.println(imgUrl);

            } catch (IOException e) {
                System.out.println("plz");
            }

        }
    }
}
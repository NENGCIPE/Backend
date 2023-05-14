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
                Document doc = Jsoup.connect(url).get(); // URL의 HTML을 가져옴

                /* 레시피명 추출 */
                String recipeName;
                Elements recipeNameElements = doc.select("div.view2_summary.st3 h3");

                if (!recipeNameElements.isEmpty()){
                    // 선택자 값이 Null이 아니면 첫번째 요소인 레시피명 가져오기.
                    Element nameElement = recipeNameElements.first();
                    recipeName = nameElement.text();
                }
                else {
                    recipeName = "null";
                }
                /* sout로 레시피명 크롤링 검증 ok */

                /* 레시피 재료 추출 */
                System.out.println(recipeName);

                Elements ingredElement = doc.getElementsByClass("ready_ingre3");
                        //selectFirst("div.ready_ingred3 ul");

                // 레시피에 필요한 재료들은 각각이 li에 재료명과 수량이 담겨있음.
                //Elements ingredients = ingredElement.getElementsByTag("li");
                Elements ingredients = ingredElement.select("li");
               // StringBuilder stuff = new StringBuilder();
                String res = "";

                /* 레시피 재료 목록 중 수량!! 부분 */
                String spanText = "";
                for (Element e : ingredients) {
                    Element spanTag = e.selectFirst("span");
                    if (spanTag != null) {
                        spanText = spanTag.text();
                    }
                    System.out.println(spanText);
                }
//                for (int i = 0; i < ingredients.size(); i++) {
//                    Element spanTag = ingredients.get(i).select("span").first();
//                    String spanText = spanTag.text();
//                    // 긴 공백이 포함된 재료명과 계량법을 담아줌
//                    String tmp = ingredients.get(i).text();
//
//                    if (spanText.length() > 0) {
//                        res = tmp.substring(0, tmp.length() - spanText.length()-1);
//                    } else {
//                        res = "null";
//                    }
//                    System.out.println(res);
//
//                }

            } catch (IOException e) {

                System.out.println("뭐");
            }

        }
    }
}
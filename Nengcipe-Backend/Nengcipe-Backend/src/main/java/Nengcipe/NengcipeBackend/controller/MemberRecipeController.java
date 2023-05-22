package Nengcipe.NengcipeBackend.controller;


import Nengcipe.NengcipeBackend.dto.MemberRecipeRequestDto;
import Nengcipe.NengcipeBackend.dto.MemberRecipeResponseDto;
import Nengcipe.NengcipeBackend.service.MemberRecipeService;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/recipes")
public class MemberRecipeController {
//    private final MemberRecipeService memberRecipeService;
//
//    public void MemberRecipeController(MemberRecipeService memberRecipeService) {
//        this.memberRecipeService = memberRecipeService;
//    }
//
////    @Autowired
////    ScrapRepository scrapRepository;
////
////    @Autowired
////    MemberRepository memberRepository;
//
////    @Autowired
////    RecipeRepository recipeRepository;
//
//    /**
//     * API : [POST] 레시피 스크랩 API
//     * @param recipeId
//     * @uri api/recipes/scrap?recipe_id=1
//     */
//    @PostMapping("/scrap")
//    public Response scrapRecipe(@RequestParam("recipe_id") Long recipeId) {
//        MemberRecipeResponseDto responseDto = memberRecipeService.scrapRecipe(requestDto.getMemberId(), requestDto.getRecipeId());
//        return Response.success(memberRecipeService.)
//    }

}

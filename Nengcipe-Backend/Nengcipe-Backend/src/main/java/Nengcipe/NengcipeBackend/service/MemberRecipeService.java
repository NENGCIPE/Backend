package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.MemberRecipeRequestDto;
import Nengcipe.NengcipeBackend.repository.MemberRecipeRepository;
import Nengcipe.NengcipeBackend.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberRecipeService {

    private final MemberRepository memberRepository;
    private final MemberRecipeRepository memberRecipeRepository;
    private final RecipeRepository recipeRepository;
    public MemberRecipeService(final MemberRecipeRepository memberRecipeRepository, final RecipeRepository recipeRepository, final MemberRepository memberRepository){
        this.memberRecipeRepository = memberRecipeRepository;
        this.memberRepository = memberRepository;
        this.recipeRepository = recipeRepository;
    }

    //POST기능
    public String createScrapRecipe(MemberRecipeRequestDto memberRecipeRequestDto){
        Long member_id = memberRecipeRequestDto.getMemberId();
        Long recipe_id = memberRecipeRequestDto.getRecipeId();

        Optional <Member> member = memberRepository.findById(member_id);
        Optional <Recipe> recipe = recipeRepository.findById(recipe_id);

        MemberRecipe newMemberRecipe = MemberRecipe.builder()
                .member(member)
                .recipe(recipe)
                .build();

        member.getMemberRecipeList().add(newMemberRecipe);
        recipe.getMemberRecipeList().add(newMemberRecipe);

        newMemberRecipe = memberRecipeRepository.save(newMemberRecipe);

        return "스크랩 성공";
    }



    //GET기능
    public String getScrapRecipe() {
        List<MemberRecipe> memberRecipeList = new ArrayList<>(this.memberRecipeRepository.findAll());
        return "스크랩 리스트 가져오기 성공"
    }

    //DELETE 기능
    public String deleteScrapRecipe(){
        return "스크랩 취소 성공"
    }



}

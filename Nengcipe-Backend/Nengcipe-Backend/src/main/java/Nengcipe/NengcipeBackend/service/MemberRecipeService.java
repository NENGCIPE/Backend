package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.MemberRecipeRequestDto;
import Nengcipe.NengcipeBackend.repository.MemberRecipeRepository;
import Nengcipe.NengcipeBackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberRecipeService {

    private final MemberRecipeRepository memberRecipeRepository;

    /**
     * API : [POST] 레시피 스크랩 API
     * @param member
     * @param recipe
     * @return newMemberRecipe
     */
    public MemberRecipe createScrapRecipe(Member member, Recipe recipe){
        MemberRecipe newMemberRecipe = MemberRecipe.memberRecipe()
                .member(member) //member_id에 해당하는 member 객체 (jwt)
                .recipe(recipe) //recipe_id에 해당하는 recipe 객체 (findbyid)
                .build();
        memberRecipeRepository.save(newMemberRecipe); //newMemberRecipe란 객체를 저장소에 저장하는 역할
        return newMemberRecipe;
    }



//    //GET기능
//    public String getScrapRecipe() {
//        List<MemberRecipe> memberRecipeList = new ArrayList<>(this.memberRecipeRepository.findAll());
//        return "스크랩 리스트 가져오기 성공";
//    }
//
//    //DELETE 기능
//    public String deleteScrapRecipe(){
//        return "스크랩 취소 성공"
//    }



}

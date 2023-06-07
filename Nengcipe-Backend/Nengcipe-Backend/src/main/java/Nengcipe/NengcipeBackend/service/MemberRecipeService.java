package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.domain.MemberRecipe;
import Nengcipe.NengcipeBackend.domain.Recipe;
import Nengcipe.NengcipeBackend.dto.MemberRecipeRequestDto;
import Nengcipe.NengcipeBackend.dto.ResultResponse;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.MemberRecipeRepository;
import Nengcipe.NengcipeBackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        MemberRecipe newMemberRecipe = MemberRecipe.myMemberRecipe()
                .member(member) //member_id에 해당하는 member 객체 (jwt)
                .recipe(recipe) //recipe_id에 해당하는 recipe 객체 (findbyid)
                .build();
        memberRecipeRepository.save(newMemberRecipe); //newMemberRecipe란 객체를 저장소에 저장하는 역할
        return newMemberRecipe;

    }


    /**
     * API : [DELETE] 레시피 스크랩 삭제 API
     * @param member
     * @param recipe
     * @return
     * @throws NotFoundException
     */
    public MemberRecipe deleteScrapRecipe(Member member, Recipe recipe) throws NotFoundException {
        Optional<MemberRecipe> delMemberRecipe = memberRecipeRepository.findByMemberAndRecipe(member, recipe);
        memberRecipeRepository.delete(delMemberRecipe.get());
        return delMemberRecipe.get();
    }



}
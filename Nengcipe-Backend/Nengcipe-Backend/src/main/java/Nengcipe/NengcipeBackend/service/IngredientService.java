package Nengcipe.NengcipeBackend.service;

import Nengcipe.NengcipeBackend.domain.Category;
import Nengcipe.NengcipeBackend.domain.Ingredient;
import Nengcipe.NengcipeBackend.domain.Member;
import Nengcipe.NengcipeBackend.dto.IngredientDto;
import Nengcipe.NengcipeBackend.exception.NotAuthorizedException;
import Nengcipe.NengcipeBackend.exception.NotFoundException;
import Nengcipe.NengcipeBackend.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    /**
     * 연관 관계 매핑을 위한 Member, Category 필요.
     * 냉장고 재료 저장.
     * 냉장고에 같은 이름이 존재한다면 quantity +1.
     */
    public Ingredient registerIngredient(IngredientDto ingredientDto, Member member, Category category) {
        Optional<Ingredient> find = ingredientRepository.findByIngredNameAndMember(ingredientDto.getIngredName(), member);
        //이미 존재한다면 quantity+1
        if (find.isPresent()) {
            find.get().addQuantity();
            ingredientRepository.save(find.get());
            return find.get();
        }
        else {
            Ingredient ingredient = Ingredient.myIngredient()
                    .ingredName(ingredientDto.getIngredName())
                    .quantity(ingredientDto.getQuantity())
                    .category(category)
                    .member(member).build();
            ingredientRepository.save(ingredient);
            return ingredient;
        }
    }
    /**
     * 유저가 냉장고 안의 재료 삭제 권한 체크 후 삭제. 권한 없다면 NotAuthorizedException 발생
     *  냉장고 재료 삭제.
     *  만약 quantity가 2이상이라면 -1.
     */
    public Ingredient deleteIngredient(Member member, Long ingredId) throws NotFoundException {

        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredId);
        if (ingredient.isEmpty()) {
            throw new NotFoundException("재료 아이디", ingredId);
        }
        Ingredient findIngredient = ingredient.get();
        if (member != findIngredient.getMember()) {
            throw new NotAuthorizedException();
        }
        //개수가 2이상이면 quantity -1
        if (ingredient.get().getQuantity() > 1) {
            ingredient.get().subQuantity();
            ingredientRepository.save(ingredient.get());
            return ingredient.get();
        }
        ingredientRepository.delete(ingredient.get());
        return ingredient.get();
    }


}

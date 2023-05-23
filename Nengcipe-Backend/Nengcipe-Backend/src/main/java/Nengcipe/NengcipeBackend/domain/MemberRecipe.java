package Nengcipe.NengcipeBackend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Recipe recipe;

//    //true = 스크랩 o , false = 스크랩 x
//    @Column(nullable = false)
//    private boolean status;

    @Builder(builderClassName = "MemberRecipe" , builderMethodName = "memberRecipe")
    public MemberRecipe(Member member, Recipe recipe) {
        this.member=member;
        this.recipe=recipe;
        member.getMemberRecipeList().add(this);
        recipe.getMemberRecipeList().add(this);
    }

}

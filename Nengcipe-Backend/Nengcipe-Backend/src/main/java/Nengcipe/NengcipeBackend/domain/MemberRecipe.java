package Nengcipe.NengcipeBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Member member;
    @ManyToOne
    private Recipe recipe;

    @Builder(builderClassName = "MyMemberRecipe" , builderMethodName = "myMemberRecipe")
    public MemberRecipe(Member member, Recipe recipe) {
        this.member=member;
        this.recipe=recipe;
        member.getMemberRecipeList().add(this);
        recipe.getMemberRecipeList().add(this);
    }


}

package Nengcipe.NengcipeBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message="이름은 필수 값입니다.")
    private String memberName;
    @NotNull(message = "아이디는 필수 값입니다.")
    private String memberId;
    @NotNull(message = "비밀번호는  필수 값입니다.")
    private String password;
    @OneToMany(mappedBy = "member")
    private List<Ingredient> ingredientList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberRecipe> memberRecipeList = new ArrayList<>();


}

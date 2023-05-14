package Nengcipe.NengcipeBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    //유저가 탈퇴하면 유저 냉장고 안의 재료 전부 삭제
    @Builder
    public Member(Long id, String memberName, String memberId, String password) {
        this.id = id;
        this.memberName = memberName;
        this.memberId = memberId;
        this.password = password;
    }
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> ingredientList = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberRecipe> memberRecipeList = new ArrayList<>();


}

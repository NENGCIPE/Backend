package Nengcipe.NengcipeBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {
    //냉장고에 저장하는 재료와 레시피에 저장하는 재료 두 가지를 같은 Ingredient 클래스로 사용.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingred_id")
    private Long id;

    private String ingredName;
    private int quantity;
    private LocalDate expiratioinDate;
    @ManyToOne
    @JsonIgnore
    private Member member;
    @ManyToOne
    private Category category;

//    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
//    private List<IngredientRecipe> ingredientRecipeList = new ArrayList<>();

    @Builder
    public Ingredient(String ingredName, Integer quantity, LocalDate expirationDate, Category category, Member member) {
        this.ingredName=ingredName;
        this.quantity=quantity;
        this.category=category;
        this.member=member;
        this.expiratioinDate = expirationDate;
        member.getIngredientList().add(this);
        category.getIngredientList().add(this); //category에 저장.
    }
    public void addQuantity() {
        this.quantity=this.quantity+1;
    }

    public void subQuantity() {
        if (this.quantity > 0) {
            this.quantity-=1;
        }
    }

}

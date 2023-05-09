package Domain;

import jakarta.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    @Column(name = "ingred_id")
    private Long id;

    private String ingredName;

    // Member와 Ingredientd의 N:1 관계 매핑.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


}

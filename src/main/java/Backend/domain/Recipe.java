package Backend.domain;

import jakarta.persistence.*;

@Entity
public class Recipe {

    @Id @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    @Column(name = "recipe_name")
    private String name;

    @Lob
    private String recipeDetail;

    //Member와 N:1 매핑
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

}
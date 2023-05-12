package Nengcipe.NengcipeBackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<Ingredient> ingredientList;


}

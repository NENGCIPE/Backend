package Nengcipe.NengcipeBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;
    private String categoryName;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Ingredient> ingredientList;
    @Builder
    public Category(String categoryName) {
        this.categoryName=categoryName;
    }


}

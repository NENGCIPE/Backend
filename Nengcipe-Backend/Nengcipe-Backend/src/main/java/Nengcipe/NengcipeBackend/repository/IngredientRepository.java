package Nengcipe.NengcipeBackend.repository;

import Nengcipe.NengcipeBackend.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

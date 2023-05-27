package Nengcipe.NengcipeBackend.repository;

import Nengcipe.NengcipeBackend.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // 재료 이름을 기반으로 레시피를 검색
    List<String> findAllByrecipeIngredNameIsNotNull();

}

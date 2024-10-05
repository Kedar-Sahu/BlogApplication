package in.main.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import in.main.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}

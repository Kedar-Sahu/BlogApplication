package in.main.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.main.entities.Category;
import in.main.entities.Post;
import in.main.entities.User;

public interface PostRepository extends JpaRepository<Post,Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
}

package in.main.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import in.main.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

}

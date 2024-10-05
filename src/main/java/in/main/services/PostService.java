package in.main.services;

import java.util.List;

import in.main.entities.Post;
import in.main.payloads.PostDto;
import in.main.payloads.PostResponse;

public interface PostService {
	
	Boolean createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	Boolean updatePost(PostDto postDto,Integer postId);
	
	PostDto getPostById(Integer postId);
	
	PostResponse getAllPost(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);
	
	Boolean deletePost(Integer postId);
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	List<PostDto> getPostsByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);

}

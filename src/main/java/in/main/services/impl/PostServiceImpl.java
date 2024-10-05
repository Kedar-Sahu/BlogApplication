package in.main.services.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.entities.Category;
import in.main.entities.Post;
import in.main.entities.User;
import in.main.exceptions.ResourceNotFoundException;
import in.main.payloads.PostDto;
import in.main.payloads.PostResponse;
import in.main.repositorys.CategoryRepository;
import in.main.repositorys.PostRepository;
import in.main.repositorys.UserRepository;
import in.main.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public Boolean createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User "," user Id ",userId));
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("User "," user Id ",userId));
		
		Post post=modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post save=postRepository.save(post);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Boolean updatePost(PostDto postDto, Integer postId) {
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post ","post Id ",postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post save=postRepository.save(post);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("postId","post Id", postId));
		PostDto postDto= modelMapper.map(post, PostDto.class);
		if(ObjectUtils.isEmpty(postDto)) {
			return null;
		}
		else {
			return postDto;
		}
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort;
		sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}
//		else {
//			sort=Sort.by(sortBy).descending();
//		}
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePost=postRepository.findAll(p);
		List<Post> allPosts=pagePost.getContent();
		
		List<PostDto> postDtoList=allPosts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotallElements(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		if(!ObjectUtils.isEmpty(postResponse)) {
			return postResponse;
		}
		else {
			return null;
		}
	}

	@Override
	public Boolean deletePost(Integer postId) {
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("postId ","post Id ",postId));
		if(!ObjectUtils.isEmpty(post)) {
			postRepository.delete(post);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","category Id ",categoryId));
		List<Post> posts=postRepository.findByCategory(category);
		List<PostDto> postDto=posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","user Id",userId));
		List<Post> posts=postRepository.findByUser(user);
		List<PostDto> postDto=posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts=postRepository.findByTitleContaining(keyword);
		List<PostDto> postDto=posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

}

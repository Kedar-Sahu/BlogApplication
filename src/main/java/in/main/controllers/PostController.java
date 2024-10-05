package in.main.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.main.configs.AppConstants;
import in.main.payloads.PostDto;
import in.main.payloads.PostResponse;
import in.main.services.FileService;
import in.main.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/createPost/user/{userId}/category/{categoryId}")
	public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		Boolean save=postService.createPost(postDto, userId, categoryId);
		if(!save) {
			return new ResponseEntity<>("post create failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("post create successfully",HttpStatus.CREATED);
		}
	}
	
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<?> update(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
		Boolean update=postService.updatePost(postDto, postId);
		if(!update) {
			return new ResponseEntity<>("post update failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("post update successfully",HttpStatus.CREATED);
		}
	}
	
	
	@GetMapping("/getPostById/{postId}")
	public ResponseEntity<?> getById(@PathVariable Integer postId){
		PostDto postDto=postService.getPostById(postId);
		if(ObjectUtils.isEmpty(postDto)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(postDto,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getAllPost")
	public ResponseEntity<?> getAll(@RequestParam(value="pageNumber" ,defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
			                        @RequestParam(value="pageSize" ,defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
		                            @RequestParam(value="sortBy" ,defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
		                            @RequestParam(value="sortDir" ,defaultValue=AppConstants.SORT_DIR,required=false)String sortDir){
		
		PostResponse postResponse=postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		if(ObjectUtils.isEmpty(postResponse)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(postResponse,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<?> delete(@PathVariable Integer postId){
		Boolean delete=postService.deletePost(postId);
		if(!delete) {
			return new ResponseEntity<>("post delete failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("post delete successfully",HttpStatus.OK);
		}
	}
	
	@GetMapping("/getPostByCategory/{categoryId}")
	public ResponseEntity<?> getByCategory(@PathVariable Integer categoryId){
		List<PostDto> postDto=postService.getPostsByCategory(categoryId);
		if(CollectionUtils.isEmpty(postDto)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(postDto,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getPostByUser/{userId}")
	public ResponseEntity<?> getByUser(@PathVariable Integer userId){
		List<PostDto> postDto=postService.getPostsByUser(userId);
		if(CollectionUtils.isEmpty(postDto)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(postDto,HttpStatus.OK);
		}
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> searchPostByTitle(@PathVariable("keyword") String keyword){
		List<PostDto> postDto=postService.searchPosts(keyword);
		if(CollectionUtils.isEmpty(postDto)) {
			return new ResponseEntity<>("search keyword is not match",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(postDto,HttpStatus.OK);
		}
	}
	
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<?> uploadPostImage(@RequestParam("image")MultipartFile image,
			                                 @PathVariable Integer postId) throws IOException{
		PostDto postDto=postService.getPostById(postId);
		String fileName=fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		Boolean update=postService.updatePost(postDto, postId);
		if(!update) {
			return new ResponseEntity<>("image upload failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {

			return new ResponseEntity<>("image upload successfully",HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response) throws IOException {
		
		InputStream resource=fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
		
	}
}
